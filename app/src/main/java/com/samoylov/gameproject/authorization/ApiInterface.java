package com.samoylov.gameproject.authorization;

import com.samoylov.gameproject.locations.LocationState;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("reg/")
    Call<User> performRegistration(@Field("name") String name, @Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("log/")
    Call<User> performUserLogin(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("attradd/")
    Call<User> add_stat(@Header("Cookie") String sessionIdAndToken, @Field("Str") double addstr, @Field("Agi") double addagi, @Field("Int") double addint, @Field("Luc") double addluc);

    @FormUrlEncoded
    @POST("attradd/")
    Call<User> addLuc(@Header("Cookie") String sessionIdAndToken, @Field("Luc") double addluc);

    @FormUrlEncoded
    @POST("bres/")
    Call<User> addExp(@Header("Cookie") String sessionIdAndToken, @Field("EXP") double addExp);

    @FormUrlEncoded
    @POST("location/")
    Call<LocationState> moveToLocation(@Header("Cookie") String sessionIdAndToken, @Field("to") double toLocationName);
    // response: 200 Ok, 500 server error, 404 location not found, 406 no path (нет такого пути)

    @DELETE("usr/")
    Call<Void> deleteUser(@Header("Cookie") String sessionIdAndToken);
    //response: 200 ok, 500 server error
    @POST("usr/")
    Call<User> your_name(@Header("Cookie") String sessionIdAndToken);
}