package com.example.school553.api;

import com.example.school553.model.ClassesRecyclerModel;
import com.example.school553.model.ContactsRecyclerModel;
import com.example.school553.model.LessonsClassRecyclerModel;
import com.example.school553.model.LessonsMainRecyclerModel;
import com.example.school553.model.OlympicEventModel;
import com.example.school553.model.OlympicRecyclerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonAPI {

    @GET("/wp-json/wp/v2/posts")
    Call<List<WPpost>> getPosts();

    @GET("/api/classes")
    Call<List<ClassesRecyclerModel>> getClasses();

    @GET("/api/contacts")
    Call<List<ContactsRecyclerModel>> getContacts();

    @GET("/api/olympic")
    Call<List<OlympicRecyclerModel>> getOlympic();

    @GET("/api/olympic/event/{event_id}")
    Call<List<OlympicEventModel>> getOlympicEvent(@Path("event_id") Integer event_id);

    @GET("/api/class/weekday")
    Call<List<LessonsMainRecyclerModel>> getClassWeekday();

    @GET("/api/class/shedule/{id_class}/{id_weekday}")
    Call<List<LessonsClassRecyclerModel>> getLessonsClass(@Path("id_class") Integer id_class,@Path("id_weekday") Integer id_weekday );

    @GET("/api/class/weekday/{class_name}")
    Call<List<LessonsMainRecyclerModel>> getOrderWeekday(@Path("class_name") String class_name);

    @GET("/api/exams")
    Call<List<OlympicRecyclerModel>> getExams();

    @GET("/api/exams/{class_id}")
    Call<List<OlympicRecyclerModel>> getExamsClass(@Path("class_id") Integer class_id);
}
