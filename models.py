import datetime
from flask import url_for
from researchorum import db

COMMENT_TYPE = (('Q', 'Help Me Understand'),
        ('C', 'Collaboration Invite'),
        ('S', 'Suggestion'))

class Post(db.DynamicDocument):
    created_at = db.DateTimeField(default=datetime.datetime.now, required=True)
    title = db.StringField(max_length=255, required=True)
    slug = db.StringField(max_length=255, required=True)
    comments = db.ListField(db.EmbeddedDocumentField('Comment'))
    display_tags = db.ListField(db.StringField(max_length=255))
    tags = db.StringField(max_length=255)
    authors = db.ListField(db.StringField(max_length=255))
    #authors = db.StringField(max_length=512)

    def get_absolute_url(self):
        return url_for('post', kwargs={"slug": self.slug})

    def __unicode__(self):
        return self.title

    @property
    def post_type(self):
        return self.__class__.__name__

    meta = {
        'allow_inheritance': True,
        'indexes': ['-created_at', 'slug', 'title'],
        'ordering': ['-created_at']
    }


class BlogPost(Post):
    body = db.StringField(required=True)

class Video(Post):
    embed_code = db.StringField(required=True)

class Image(Post):
    image_url = db.StringField(required=True, max_length=255)

class Quote(Post):
    body = db.StringField(required=True)
    author = db.StringField(verbose_name="Author Name", required=True, max_length=255)

class Comment(db.EmbeddedDocument):
    created_at = db.DateTimeField(default=datetime.datetime.now, required=True)
    body = db.StringField(verbose_name="Comment", required=True)
    author = db.StringField(verbose_name="Name", max_length=255, required=True)
    comment_type = db.StringField(required=True, choices=COMMENT_TYPE)

