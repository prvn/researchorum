package com.researchorum.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author pbathala
 */
@Document(collection = "posts")
public class Record {

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("slug")
    private String slug;

    @Field("image")
    private String image;

    @Field("tags")
    private List<String> tags;

    @Field("comments")
    private List<Comment> comments;

    @Field("authors")
    private List<String> authors;

    @Field("body")
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("id: " + id + ", ");
        sb.append("body: " + body + ", ");
        sb.append("title: " + title + ", ");
        sb.append("slug: " + slug + ", ");
        sb.append("tags: " + tags.toString() + ", ");
        sb.append("comments: " + comments.toString() + ", ");
        sb.append("authors: " + authors.toString());
        sb.append("]");

        return sb.toString();
    }
}

class Comment {

    private CommentType commentType;
    private String comment;
    private String user;
}

enum CommentType {

    QUESTION,
    SUGGESTION;
}