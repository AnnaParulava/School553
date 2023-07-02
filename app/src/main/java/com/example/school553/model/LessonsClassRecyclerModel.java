package com.example.school553.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LessonsClassRecyclerModel {
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("weekday")
    @Expose
    private String weekday;
    @SerializedName("office")
    @Expose
    private String office;

    public LessonsClassRecyclerModel(String number, String title, String weekday, String office) {
        this.number = number;
        this.title = title;
        this.weekday = weekday;
        this.office = office;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
