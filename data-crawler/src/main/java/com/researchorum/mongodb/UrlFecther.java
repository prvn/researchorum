package com.researchorum.mongodb;

import com.mongodb.*;
import com.researchorum.utils.Config;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author pbathala
 */
public class UrlFecther {

    private static final Logger logger = Logger.getLogger(UrlFecther.class);

    private static final List<String> NON_ACM_LINKS = Arrays.asList(new String[]{
            "doi.ieeecomputersociety.org", "ieee", "ippserv.ugent.be", "springer", "computer.org",
            "www.graphicsinterface.org", "www.sigda.org", "decsai.ugr.es", "dx.doi.org/10.1007",
            "dx.doi.org/10.1109", "dx.doi.org/10.1117", "dx.doi.org/10.1093", "dx.doi.org/10.2991",
            "dx.doi.org/10.1016", "dx.doi.org/10.1002", "dx.doi.org/10.1504", "dx.doi.org/10.1023",
            "dx.doi.org/10.1137", "www.usenix.org", "www.zpid.de", "www.supercomp.org", "www.stringology.org",
            "psc.felk.cvut.cz", "www2003.org", "sunsite.informatik.rwth-aachen.de", "arxiv.org",
            "www.igi-pub.com", "www.fujipress.jp", "jair.org", "www.cs.washington.edu", "www.acmqueue.org"});

    private static final List<String> NON_ACM_SUFFIXES = Arrays.asList(new String[]{"pdf", "pdf.gz", "ps", "ps.gz", "doc"});

    private MongoClient mongoClient;
    private DB db;
    private DBCollection collection;
    private UrlContainer urlContainer = new UrlContainer();

    public UrlFecther(String db, String collection) throws Exception {
        this("localhost", 27017, db, collection);
    }

    public UrlFecther(String host, int port, String db, String collection) throws Exception {
        this.mongoClient = new MongoClient(host, port);
        this.db = mongoClient.getDB(db);
        this.collection = this.db.getCollection(collection);
    }

    /**
     * Gets the electronic edition links of dblp from mongodb
     * as a container
     *
     * @return {@link UrlContainer} Container that holds different types of links by publication
     */
    public UrlContainer getLinks() {

        List<String> acmLinks = new ArrayList<String>();
        List<String> ieeeLinks = new ArrayList<String>();

        /*
            Get documents that only have source url, but not abstract and publication.

            We also add only publication for articles that doesnt have any abstract on their
            respective web sites.
         */
        DBObject query = new BasicDBObject("sourceUrl", new BasicDBObject("$exists", true));
        query.put("abstract", new BasicDBObject("$exists", false));

        DBObject filterQuery = new BasicDBObject("sourceUrl", 1);
        filterQuery.put("_id", 0);

        DBCursor dbCursor = collection.find(query, filterQuery);
        //System.out.println("Total Url's: " + dbCursor.count());
        logger.info("Total Url's: " + dbCursor.count());

        try {
            while (dbCursor.hasNext()) {
                String link = dbCursor.next().get("sourceUrl").toString();
                //logger.info(link);
                if (isAcmLink(link)) {
                    acmLinks.add(link + "&preflayout=flat");
                }
                if (isIeeeLink(link)) {
                    ieeeLinks.add(link);
                }
            }
        } finally {
            dbCursor.close();
        }

        urlContainer.setAcmUrls(acmLinks);
        urlContainer.setIeeeUrls(ieeeLinks);

        return urlContainer;
    }

    private boolean isAcmLink(String link) {
        boolean allowed = true;

        if (!link.contains("acm.org") && !link.contains("doi")) {
            allowed = false;
        }

        for (String l : NON_ACM_LINKS) {
            if (link.contains(l)) {
                allowed = false;
            }
        }

        for (String s : NON_ACM_SUFFIXES) {
            if (link.endsWith(s)) {
                allowed = false;
            } else {
                allowed = true;
            }
        }

        return allowed;
    }

    private boolean isIeeeLink(String link) {
        if (link.contains("ieee")) {
            return true;
        }
        return false;
    }

    public static List<String> getAvailableLinks() {
        return Arrays.asList(new String[]{UrlContainer.ACM_LINKS, UrlContainer.IEEE_LINKS});
    }

    public static void main(String[] args) throws Exception {
        new UrlFecther(Config.MONGODB_DB, Config.MONGODB_COLLECTION).getLinks();
    }
}
