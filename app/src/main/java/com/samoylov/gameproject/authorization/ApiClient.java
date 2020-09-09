package com.samoylov.gameproject.authorization;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL="http://92.222.232.187:8000";
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient(){
        if ((retrofit==null)){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}