package com.example.school553.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlympicRecyclerModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;

    public OlympicRecyclerModel(Integer id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
