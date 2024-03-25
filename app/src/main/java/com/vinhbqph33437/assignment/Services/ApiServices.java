package com.vinhbqph33437.assignment.Services;

import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Model.Student;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServices {
    public static String BASE_URL = "http://192.168.1.162:3000/api/";
//    public static String BASE_URL = "http://10.24.20.198:3000/api/";

    @GET("get-list-student")
    Call<Response<ArrayList<Student>>> getListStudent();

    @POST("add-student")
    Call<Response<Student>> addStudent(@Body Student student);

    @DELETE("delete-student-by-id/{id}")
    Call<Response<Student>> deleteStudentById(@Path("id") String id);

    @PUT("update-student-by-id/{id}")
    Call<Response<Student>> updateStudentById(@Path("id") String id, @Body Student student);
}
