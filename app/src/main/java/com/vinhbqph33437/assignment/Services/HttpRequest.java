package com.vinhbqph33437.assignment.Services;

import static com.vinhbqph33437.assignment.Services.ApiServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private ApiServices requestInterface;

    public HttpRequest(){
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class);
    }

    public ApiServices callAPI(){
        return requestInterface;
    }
}
