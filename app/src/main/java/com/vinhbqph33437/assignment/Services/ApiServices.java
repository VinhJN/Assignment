package com.vinhbqph33437.assignment.Services;

import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Model.Student;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    //    public static String BASE_URL = "http://192.168.1.162:3000/api/";
//    public static String BASE_URL = "http://10.24.20.198:3000/api/";
    public static String BASE_URL = "http://192.168.100.11:3000/api/";


    @GET("get-list-student")
    Call<Response<ArrayList<Student>>> getListStudent();

    @Multipart
    @POST("add-student-with-file-image")
    Call<Response<Student>> addStudentWithFileImage(@PartMap Map<String, RequestBody> requestBodyMap,
                                                @Part ArrayList<MultipartBody.Part> ds_hinh
    );

    @DELETE("delete-student-by-id/{id}")
    Call<Response<Student>> deleteStudentById(@Path("id") String id);

    @Multipart
    @PUT("update-student-by-id/{id}")
    Call<Response<Student>> updateStudentWithFileImage(@PartMap Map<String, RequestBody> requestBodyMap,
                                                   @Path("id") String id,
                                                   @Part ArrayList<MultipartBody.Part> ds_hinh
    );

    @GET("search-student")
    Call<Response<ArrayList<Student>>> searchStudent(@Query("key") String key);

    @GET("sort-student-by-name")
    Call<Response<ArrayList<Student>>> sortStudentsByName();

}
