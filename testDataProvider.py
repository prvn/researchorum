#!/bin/env python
################################
# Test data provider for 
# testing purposes
#
# @since: July 02, 2013
################################

from dataProvider import DataProvider

class TestDataProvider(DataProvider):
    """
    A Test data provider implementation
    """

    def getAccount(self, username=None):
        if username is None:
            return None

        account = {}
        account['username'] = username
        account['fname'] = "John"
        account['lname'] = "Doe"
        account['email'] = "john.doe@researchorum.com"
        account['posts'] = 5
        account['followers'] = ['aj', 'prvn', 'darsn']

        return account

    def getResults(self, query=None):
        if query is None:
            return None

        results = []

        paper1 = {}
        paper1['title'] = "MapReduce : Simplified Data Processing on Large Clusters"
        paper1['author'] = ["Jeffrey Dean", "Sanjay Ghemawat"]
        paper1['domain'] = "Computer and Information Science"

        paper2 = {}
        paper2['title'] = "The anatomy of a large-scale hypertextual Web search engine"
        paper2['author'] = ["S Brin", "L Page"]
        paper2['domain'] = "Computer and Information Science"

        results.append(paper1)
        results.append(paper2)

        return results

if __name__ == '__main__':
    dataProvider = TestDataProvider()

