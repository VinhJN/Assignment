package com.vinhbqph33437.assignment.Interface;

import com.vinhbqph33437.assignment.Model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/")
    Call<List<Student>> getStudents();


}
