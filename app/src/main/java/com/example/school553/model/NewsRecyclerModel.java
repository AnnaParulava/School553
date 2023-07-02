package com.example.school553.model;

public class NewsRecyclerModel {
    private int id;
    private String title;
    private String link;
    private String date;
    private String excerpt;

    public NewsRecyclerModel(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public NewsRecyclerModel(String title, String link, String excerpt) {
        this.title = title;
        this.link = link;
        this.excerpt = excerpt;
    }

    public NewsRecyclerModel(int id, String title, String link, String excerpt, String date) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.excerpt = excerpt;
        this.date = date;
    }

    public NewsRecyclerModel(String title, String link, String excerpt, String date) {
        this.title = title;
        this.link = link;
        this.excerpt = excerpt;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
