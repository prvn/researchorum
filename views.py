from flask import Blueprint, request, redirect, render_template, url_for
from flask.views import MethodView
from researchorum.models import Post, Comment
from flask.ext.mongoengine.wtf import model_form

from Util import *
import math

import random

posts = Blueprint('posts', __name__, template_folder='templates')

class ListView(MethodView):

    items_per_page = 2
    
    def get(self):
        query_params = request.args
        ipp = 10
        page = 1

        if len(query_params) != 0:
            if query_params['ipp']:
                ipp = int(str(query_params['ipp']))
            else:
                ipp = self.items_per_page

            if query_params['pg']:
                page = int(str(query_params['pg']))

        print "IPP: ", ipp, "Page: ", page

        if page > 1:
            posts = Post.objects[(ipp*(page-1)):(ipp*page)]
        else:
            posts = Post.objects[:(ipp*page)]

        total_posts = len(Post.objects)

        print "Total Posts: ", total_posts

        pagination_urls = []

        if (total_posts > ipp):
            # Add pagination
            pp = total_posts/float(ipp)
            print pp
            print "Range: ", math.ceil(pp)
            for i in range(1, int(math.ceil(pp)+1)):
                url = Util.appendParam(request.base_url, "ipp", str(ipp), True)
                pagination_urls.append(Util.appendParam(url, "pg", str(i), False))

        print pagination_urls

        # add random images before returning posts
        for post in posts:
            rand = random.randrange(1, 8)
            post.image = request.base_url + "static/images/" + str(rand) + ".jpg"

        context = {
            "posts" : posts,
            "pagination_urls" : pagination_urls,
            "ipp" : ipp,
            "pg" : page
        }

        return render_template('posts/list.html', **context)

class SearchTitleView(MethodView):

    def get(self, query):
        if query is not None:
            posts = Post.objects(title__icontains=query)

        return render_template('posts/list.html', posts=posts, query=query)

class SearchTagView(MethodView):

    def get(self, tag):
        if tag is not None:
            print "Query: ", tag
            posts = Post.objects(display_tags__all=[tag])

        return render_template('posts/list.html', posts=posts, query=("#"+str(tag)))

class DetailView(MethodView):

    form = model_form(Comment, exclude=['created_at'])

    def get_context(self, slug):
        post = Post.objects.get_or_404(slug=slug)
        form = self.form(request.form)

        context = {
                "post": post,
                "form": form
                }
        return context

    def get(self, slug):
        context = self.get_context(slug)
        return render_template('posts/detail.html', **context)

    def post(self, slug):
        context = self.get_context(slug)
        form = context.get('form')

        if form.validate():
            comment = Comment()
            form.populate_obj(comment)

            post = context.get('post')
            post.comments.append(comment)
            post.save()

            return redirect(url_for('posts.detail', slug=slug))

        return render_template('posts/detail.html', **context)

posts.add_url_rule('/', view_func=ListView.as_view('list'), methods=['GET',])
posts.add_url_rule('/search/<string:query>', view_func=SearchTitleView.as_view('search-title-list'), methods=['GET',])
posts.add_url_rule('/search/tag/<string:tag>', view_func=SearchTagView.as_view('search-tags-list'), methods=['GET',])
posts.add_url_rule('/<slug>/', view_func=DetailView.as_view('detail'))
