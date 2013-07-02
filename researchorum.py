#!/bin/env python
####################################
# Researchorum Application
#
# @since: June 29, 2013
####################################

from flask import Flask
from flask import request
from flask import render_template

#better to hardcode package name, improves flask debugging
app = Flask('researchorum')


def _get_account(username=None):
    """
    Call to get user accounts, and/or authentication workflow

    """
    #return a dummy dict for now
    return {'username': username, 'name': 'John Doe', 'Research medals': 5}


def _get_search_results(query=None):
    """
    call search routine to return a data structure, (tbd), and render that to
    the search results page

    :param query: A query parameter, default none to give not found
    """
    #return an empty dict for now
    return {'payload': '{'+query+'}'}


@app.route("/")
def home():
    return render_template("home.html")


@app.route("/login", methods=['POST'])
def login():
    error = None
    account = _get_account(request.form['username'])
    # print account
    return render_template("login.html", acc=account, err=error)


@app.route("/search", methods=['POST'])
def searchPage():
    error = None
    search = _get_search_results(request.form['search'])
    # print search
    return render_template("search.html", search=search, err=error)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
