package com.example.school553.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LessonsMainRecyclerModel {
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("class_title")
    @Expose
    private String class_title;
    @SerializedName("weekday")
    @Expose
    private String weekday;
    @SerializedName("class_title_id")
    @Expose
    private Integer class_title_id;
    @SerializedName("weekday_id")
    @Expose
    private Integer weekday_id;

    List<LessonsClassRecyclerModel> lessonsItemList;

    public LessonsMainRecyclerModel(Integer number, String class_title, String weekday, List<LessonsClassRecyclerModel> lessonsItemList,
                                    Integer class_title_id, Integer weekday_id) {
        this.number = number;
        this.class_title = class_title;
        this.weekday = weekday;
        this.lessonsItemList = lessonsItemList;
        this.class_title_id = class_title_id;
        this.weekday_id = weekday_id;
    }

//    public LessonsMainRecyclerModel(Integer class_title, Integer weekday, List<LessonsClassRecyclerModel> lessonsItemList) {
//        this.class_title = class_title;
//        this.weekday = weekday;
//        this.lessonsItemList = lessonsItemList;
//    }

    public LessonsMainRecyclerModel(String class_title, String weekday) {
        this.class_title = class_title;
        this.weekday = weekday;
    }

    public  Integer getNumber() {
        return number;
    }

    public  void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getClass_title_id() {
        return class_title_id;
    }

    public void setClass_title_id(Integer class_title_id) {
        this.class_title_id = class_title_id;
    }

    public Integer getWeekday_id() {
        return weekday_id;
    }

    public void setWeekday_id(Integer weekday_id) {
        this.weekday_id = weekday_id;
    }

    public List<LessonsClassRecyclerModel> getLessonsItemList() {
        return lessonsItemList;
    }

    public void setLessonsItemList(List<LessonsClassRecyclerModel> lessonsItemList) {
        this.lessonsItemList = lessonsItemList;
    }

    public String getClass_title() {
        return class_title;
    }

    public void setClass_title(String class_title) {
        this.class_title = class_title;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

}
