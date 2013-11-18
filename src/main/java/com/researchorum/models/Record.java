package com.researchorum.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author pbathala
 */
@Document(collection = "records")
public class Record {

    public static final String TITLE_FIELD = "title";
    private static final String DEFAULT_IMAGE_URL = "/resources/images/default_profile_pic.jpg";

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("year")
    private int year;

    @Field("recordType")
    private String recordType;

    @Field("slug")
    private String slug;

    @Field("image")
    private String image;

    @Field("tags")
    private List<String> tags;

    @Field("comments")
    private List<Comment> comments;

    @Field("authorsList")
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        if (image == null || image.isEmpty()) {
            image = DEFAULT_IMAGE_URL;
        }
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
        sb.append("year: " + year + ", ");
        sb.append("recordType: " + recordType + ", ");
        sb.append("title: " + title + ", ");
        sb.append("slug: " + slug + ", ");
        if (tags != null) {
            sb.append("tags: " + tags.toString() + ", ");
        }
        if (comments != null) {
            sb.append("comments: " + comments.toString() + ", ");
        }
        if (authors != null) {
            sb.append("authors: " + authors.toString());
        }
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