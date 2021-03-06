package com.hj.diaryproject.models;

import java.io.Serializable;

/**
 * Created by USER on 2017-02-09.
 */

public class Page implements Serializable {
    private long id;
    private String title;
    private String content;
    private String image;
    private String comment;
    private int state;

    public Page(String title, String content, String image, String comment, int state) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.comment = comment;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", comment='" + comment + '\'' +
                ", state=" + state +
                '}';
    }
}
