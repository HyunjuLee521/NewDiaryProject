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


    // TODO db에서 값 받아올 경우 -> id 자동생성되므로 생성자에서 파라메터값으로 받지 않는다
    public Page(int id, String title, String content, String image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
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


    @Override
    public String toString() {
        return "Page{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id=" + id +
                ", image='" + image + '\'' +
                '}';
    }
}