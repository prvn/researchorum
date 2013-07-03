#!/bin/env python
###############################
# Interface for data provider
#
# @since: July 02, 2013
###############################

class DataProvider:
    """ 
    Data provider interface 
    """

    def getAccount(self, username=None):
        """
        Gets user account by username 
        """
        pass

    def getResults(self, query=None):
        """
        Gets results by query
        """
        pass
