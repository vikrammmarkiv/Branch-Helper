package com.example.branchiohelper.utils;

/* Created by Vikram on 18-01-2020. */

import com.example.branchiohelper.events.LinkCreatedEvent;
import com.example.branchiohelper.interfaces.LinkHelper;
import com.example.branchiohelper.models.LinkCreateResponse;
import com.google.gson.JsonElement;
import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.branchiohelper.constants.Constants.BRANCH_KEY;

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

            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
