package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.branchiohelper.constants.Constants;
import com.example.branchiohelper.events.LinkCreatedEvent;
import com.example.branchiohelper.events.RequestCompletedEvent;
import com.example.branchiohelper.interfaces.LinkHelper;
import com.example.branchiohelper.models.LinkCreate;
import com.example.branchiohelper.models.LinkCreateResponse;
import com.example.branchiohelper.utils.LinkUtils;
import com.example.branchiohelper.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    String branch_key;
    LinkHelper service;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = APIClient.getClient();
        service = retrofit.create(LinkHelper.class);
        responseText = findViewById(R.id.response);

        findViewById(R.id.create_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkUtils.createLink(service);
            }
        });
    }

    void readLink(LinkHelper service, String url){

        Call<JsonElement> linkReadCall = service.linkRead(url, branch_key);
        linkReadCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                responseText.setText(responseText.getText()+"++++"+response.body().toString());

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    @Subscribe()
    public void onLinkCreatedEvent(LinkCreatedEvent event) {
        responseText.setText(event.getResponse().getUrl());
    }

    @Subscribe()
    public void onLinkReadEvent(RequestCompletedEvent event) {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
