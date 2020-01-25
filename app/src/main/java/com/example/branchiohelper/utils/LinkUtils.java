package com.example.branchiohelper.utils;

/* Created by Vikram on 18-01-2020. */

import com.example.branchiohelper.events.LinkCreatedEvent;
import com.example.branchiohelper.events.LinkReadEvent;
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

    public static void readLink(LinkHelper service, String url){
        Call<JsonElement> linkReadCall = service.linkRead(url, BRANCH_KEY);
        linkReadCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonObject jsonObject = response.body().getAsJsonObject();
                EventBus.getDefault().post(new LinkReadEvent(jsonObject.get("data").toString()));
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
    public static ArrayList<String[]> convertLinkData(JsonElement data){
        ArrayList<String[]> linkData = new ArrayList<>();
        HashMap<String,String> convertedData = new Gson().fromJson(data.getAsString(), new TypeToken<HashMap<String, String>>(){}.getType());
        linkData.add((String[]) convertedData.keySet().toArray());
        linkData.add((String[]) convertedData.values().toArray());
        return linkData;
    }
}
