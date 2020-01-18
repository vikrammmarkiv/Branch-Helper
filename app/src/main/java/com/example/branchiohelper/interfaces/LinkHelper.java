package com.example.branchiohelper.interfaces;

import com.example.branchiohelper.models.LinkCreate;
import com.example.branchiohelper.models.LinkCreateResponse;
import com.example.branchiohelper.models.LinkUpdate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LinkHelper {
    @POST("v1/url")
    Call<LinkCreateResponse> linkCreate(@Body HashMap<String, Object> body);

    @GET("v1/url")
    Call<JsonElement> linkRead(@Query("url") String url, @Query("branch_key") String branch_key);

    @PUT("v1/url")
    Call<JsonObject> linkUpdate(@Query("url") String url, @Body HashMap<String, Object> body);

    @DELETE("v1/url")
    Call<JsonObject> linkDelete(@Query("url") String url, @Body HashMap<String, Object> body);

    @POST("v1/url/bulk/{key}")
    Call<JsonArray> linkBulkCreate(@Path ("key") String branch_key, @Body HashMap<String, Object> body);

}