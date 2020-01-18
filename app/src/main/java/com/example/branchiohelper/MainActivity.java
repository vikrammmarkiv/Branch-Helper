package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.branchiohelper.interfaces.LinkHelper;
import com.example.branchiohelper.models.LinkCreate;
import com.example.branchiohelper.models.LinkCreateResponse;
import com.example.branchiohelper.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

        branch_key = getString(R.string.branch_key);
        retrofit = APIClient.getClient();
        service = retrofit.create(LinkHelper.class);

       responseText = findViewById(R.id.response);

        findViewById(R.id.create_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLink(service);
            }
        });





    }

    void createLink(LinkHelper service){

        HashMap<String,Object> linkCreateBody = new HashMap<>();
        linkCreateBody.put("branch_key",branch_key);
        linkCreateBody.put("channel","Helper");
        linkCreateBody.put("campaign","XXX");

        HashMap<String,String> linkData = new HashMap<>();
        linkData.put("abcd","efgh");
        linkCreateBody.put("data",linkData);

        RequestBody requestBody = RequestBody.create(APIClient.JSON, new JSONObject(linkCreateBody).toString());

        Toast.makeText(this, Utils.bodyToString(requestBody),Toast.LENGTH_LONG).show();

        Call<LinkCreateResponse> linkCreateCall = service.linkCreate(requestBody);

        linkCreateCall.enqueue(new Callback<LinkCreateResponse>() {
            @Override
            public void onResponse(Call<LinkCreateResponse> call, Response<LinkCreateResponse> response) {
                LinkCreateResponse linkCreateResponse = response.body();
                if (linkCreateResponse != null) {
                    responseText.setText(linkCreateResponse.getUrl());

                    readLink(service, linkCreateResponse.getUrl());
                }
                else
                    responseText.setText(getString(R.string.error1));
            }
            @Override
            public void onFailure(Call<LinkCreateResponse> call, Throwable t) {
                responseText.setText(getString(R.string.failed));
            }
        });
    }

    void readLink(LinkHelper service, String url){

        Call<JsonElement> linkReadCall = service.linkRead("https://vikram109.app.link/P7O7wiUsT0", branch_key);
        linkReadCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                responseText.setText(responseText.getText()+"++++"+response.toString());

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }
}
