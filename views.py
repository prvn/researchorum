from flask import Blueprint, request, redirect, render_template, url_for
from flask.views import MethodView
from researchorum.models import Post, Comment
from flask.ext.mongoengine.wtf import model_form

posts = Blueprint('posts', __name__, template_folder='templates')

class ListView(MethodView):
    
    def get(self):
        posts = Post.objects.all()
        return render_template('posts/list.html', posts=posts)

class SearchView(MethodView):

    def get(self, query):
        if query is not None:
            posts = Post.objects(title__icontains=query)

        return render_template('posts/list.html', posts=posts, query=query)

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
posts.add_url_rule('/search/<string:query>', view_func=SearchView.as_view('search-list'), methods=['GET',])
posts.add_url_rule('/<slug>/', view_func=DetailView.as_view('detail'))
