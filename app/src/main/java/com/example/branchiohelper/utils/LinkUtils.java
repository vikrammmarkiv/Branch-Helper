package com.example.branchiohelper.utils;

/* Created by Vikram on 18-01-2020. */

import com.example.branchiohelper.events.LinkCreatedEvent;
import com.example.branchiohelper.events.LinkDeleteEvent;
import com.example.branchiohelper.events.LinkReadEvent;
import com.example.branchiohelper.events.LinkUpdatedEvent;
import com.example.branchiohelper.interfaces.LinkHelper;
import com.example.branchiohelper.models.LinkCreateResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.branchiohelper.utils.Utils.BRANCH_KEY;


public class LinkUtils {

    public static void createLink(LinkHelper service, HashMap<String,Object> linkCreateBody){

        Call<LinkCreateResponse> linkCreateCall = service.linkCreate(linkCreateBody);

        linkCreateCall.enqueue(new Callback<LinkCreateResponse>() {
            @Override
            public void onResponse(Call<LinkCreateResponse> call, Response<LinkCreateResponse> response) {
                LinkCreateResponse linkCreateResponse = response.body();
                EventBus.getDefault().post(new LinkCreatedEvent(linkCreateResponse));
            }
            @Override
            public void onFailure(Call<LinkCreateResponse> call, Throwable t) {

            }
        });
    }

    public static void updateLink(LinkHelper service, String url, HashMap<String,Object> linkUpdateBody){
        Call<JsonElement> linkUpdateCall = service.linkUpdate(url, linkUpdateBody);
        linkUpdateCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonObject jsonObject = null;
                if (response.body() != null) {
                    jsonObject = response.body().getAsJsonObject();
                    EventBus.getDefault().post(new LinkUpdatedEvent(jsonObject.get("data").toString()));
                }
                else
                    EventBus.getDefault().post(new LinkUpdatedEvent(response.message()));
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    public static void readLink(LinkHelper service, String url){
        Call<JsonElement> linkReadCall = service.linkRead(url, BRANCH_KEY);
        linkReadCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonObject jsonObject = null;
                if (response.body() != null) {
                    jsonObject = response.body().getAsJsonObject();
                    EventBus.getDefault().post(new LinkReadEvent(jsonObject.get("data").toString()));
                }
                else
                    EventBus.getDefault().post(new LinkReadEvent(""));
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    public static void deleteLink(LinkHelper service, String url, HashMap<String,Object> linkDeleteBody){
        Call<JsonElement> linkDeleteCall = service.linkDelete(url,linkDeleteBody);
        linkDeleteCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonObject jsonObject = response.body().getAsJsonObject();
                EventBus.getDefault().post(new LinkDeleteEvent(jsonObject.get("deleted").toString()));
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
