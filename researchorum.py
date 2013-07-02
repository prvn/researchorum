#!/bin/env python
####################################
# Researchorum Application
#
# @since: June 29, 2013
####################################

from flask import Flask
from flask import request
from flask import render_template

app = Flask(__name__)

@app.route("/")
def home():
    return render_template("home.html")

@app.route("/login", methods=['POST'])
def login():
    error = None
    account = {}
    username = request.form['username']
    account['username'] = username
    
    return render_template("login.html", acc=account, err=error)

@app.route("/search", methods=['POST'])
def searchPage():
    error = None
    search = {}
    query = request.form['search']
    search['query'] = query
    
    return render_template("search.html", search=search, err=error)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
