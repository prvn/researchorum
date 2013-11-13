#!/bin/env python

import sys
from xml.dom import minidom
import requests
import requesocks
import pymongo
from pymongo import MongoClient
import bs4
import Queue
import threading
import time

USE_REQUESOCKS = True

NON_ACM_LINKS = ["doi.ieeecomputersociety.org", "ieee", "ippserv.ugent.be", "springer", "computer.org", "www.graphicsinterface.org", "www.sigda.org", "decsai.ugr.es", "dx.doi.org/10.1007", "dx.doi.org/10.1109", "dx.doi.org/10.1117", "dx.doi.org/10.1093", "dx.doi.org/10.2991", "dx.doi.org/10.1016", "dx.doi.org/10.1002", "dx.doi.org/10.1504", "dx.doi.org/10.1023", "dx.doi.org/10.1137", "www.usenix.org", "www.zpid.de", "www.supercomp.org", "www.stringology.org", "psc.felk.cvut.cz", "www2003.org", "sunsite.informatik.rwth-aachen.de", "arxiv.org", "www.igi-pub.com", "www.fujipress.jp", "jair.org", "www.cs.washington.edu", "www.acmqueue.org"]

NON_ACM_SUFFIXES = ["pdf", "pdf.gz", "ps", "ps.gz", "doc"]

client = None
db = None
collection = None
exitFlag = 0

threadList = ["Thread-1", "Thread-2", "Thread-3", "Thread-4", "Thread-5"]
queueLock = threading.Lock()
workQueue = Queue.Queue()
threads = []
threadID = 1

kind = ""

class myThread (threading.Thread):
    def __init__(self, threadID, name, q):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.q = q
    
    def run(self):
        #print "Starting " + self.name
        process_data(self.name, self.q)
        #print "Exiting " + self.name
        
def process_data(threadName, q):
    while not exitFlag:
        queueLock.acquire()
        if not workQueue.empty():
            link = q.get()
            if kind == "acm":
                extract_acm_abstract(link)
            elif kind == "ieee":
                extract_ieee_abstract(link)

            queueLock.release()
            #print "%s processing %s" % (threadName, link)
        else:
            queueLock.release()
        
        time.sleep(1)

def get_links():
    all_links = []
    acm_links = []
    ieee_links = []

    global client
    global db
    global collection
    client = MongoClient()
    db = client.dblp
    collection = db.records

    cursor = collection.find({"sourceUrl" : {"$exists": True}, "abstract" : {"$exists" : False}}, {"sourceUrl" : 1, "_id" : 0})

    for kv in cursor:
        all_links.append(kv['sourceUrl'])

    for i in all_links:
        allowed = True

        # Check if links are IEEE, else pass
        try:
            if i.lower().strip().index("ieeecomputersociety") >= 0:
                ieee_links.append(i.strip())
        except:
            pass

        # Check if links are ACM, the do some processing to make 
        # URL parsable via bot
        try:
            if i.lower().strip().index("acm.org") < 0 and i.lower().strip().index("doi") < 0:
                allowed = False
        except:
            continue 

        for l in NON_ACM_LINKS:
            try:
                if i.lower().strip().index(l) >= 0:
                    allowed = False
            except Exception:
                pass

        for l in NON_ACM_SUFFIXES:
            if i.lower().strip().endswith(l):
                allowed = False
            else:
                allowed = True
                break

        if allowed:
            acm_links.append(str(i) + str("&preflayout=flat"))

    print "Total ACM links: ", len(acm_links)
    print "Total IEEE links: ", len(ieee_links)
    print ieee_links[:10]
    return (acm_links, ieee_links)

def get_abstracts(links, kind):
    global workQueue
    global queueLock
    global threadList
    global threadID
    global threads

    # Create new threads
    for tName in threadList:
        thread = myThread(threadID, tName, workQueue)
        thread.start()
        threads.append(thread)
        threadID += 1

    # Fill the queue
    queueLock.acquire()
    for link in links:
        workQueue.put(link)
    queueLock.release()

    # Wait for queue to empty
    while not workQueue.empty():
        pass

    # Wait for all threads to complete
    for t in threads:
        t.join()

def extract_ieee_abstract(link):
    resp = None
    data = None
    status = 0
    global collection

    if USE_REQUESOCKS:
        session = requesocks.session() 
        session.proxies = {'https': 'socks5://127.0.0.1:9050', 'http': 'socks5://127.0.0.1:9050'}
        resp = session.get(link)
    else:
        resp = requests.get(link)

    status = resp.status_code
    if status == 200:
        data = resp.text

        soup = bs4.BeautifulSoup(data)


def extract_acm_abstract(link):
    resp = None
    data = None
    status = 0
    global collection

    if USE_REQUESOCKS:
        session = requesocks.session() 
        session.proxies = {'https': 'socks5://127.0.0.1:9050', 'http': 'socks5://127.0.0.1:9050'}
        resp = session.get(link)
    else:
        resp = requests.get(link)

    status = resp.status_code
    if status == 200:
        data = resp.text
        try:
            if data.index("ACM Digital Library") >= 0:
                soup = bs4.BeautifulSoup(data)
                abs_div = soup.find('div', {'style' : 'display:inline'})
                if abs_div is not None:
                    abstract = abs_div.find_all('p')
                    whole_abstract = ""
                    print abstract
                    for paragraph in abstract:
                        s = ""
                        for line in paragraph:
                            s = s + line.encode('utf-8')

                        whole_abstract = whole_abstract + s
                
                    # Write whole_abstract to mongo here
                    url = link.split('&')[0]
                    collection.update({"sourceUrl" : url}, {"$set" : {"abstract" : whole_abstract, "publication" : "ACM"}})
                    status = resp.status_code
                else:
                    print "No abstract available"
                    collection.update({"sourceUrl" : url}, {"$set" : {"abstract" : "none", "publication" : "ACM"}})
        except ValueError:
            status = resp.status_code 
            #print "Not a ACM link"
    else:
        #print "Failed to fetch page - ", resp.status_code
        status = resp.status_code

    if not USE_REQUESOCKS:
        if resp is not None:
            resp.close()

    print link, " - ", status

def setKind(k):
    global kind
    kind = k

if __name__ == "__main__":
    acm_links, ieee_links = get_links()
    sys.exit()
    if sys.argv[1] == "acm":
        get_abstracts(acm_links, "acm")
        setKind("acm")
    elif sys.argv[1] == "ieee":
        get_abstracts(ieee_links, "ieee")
        setKind("ieee")
