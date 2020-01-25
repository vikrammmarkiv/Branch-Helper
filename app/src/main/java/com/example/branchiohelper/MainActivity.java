package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.branchiohelper.views.AppConfigDialog;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    String branch_key;
    Retrofit retrofit;
    ImageView settings;
    private CardView linkCreate;
    public static LinkHelper service;


    @OnClick(R.id.link_read) void readLink(){
        startActivity(new Intent(this,LinkReadActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = APIClient.getClient();
        responseText = findViewById(R.id.response);
        linkCreate = findViewById(R.id.link_create);
        settings = findViewById(R.id.settings);

        init();

        findViewById(R.id.link_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LinkCreateActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfigDialog dialog = new AppConfigDialog(MainActivity.this);
                dialog.showDialog();
            }
        });
    }

    void init(){
        ButterKnife.bind(this);
        SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);
        Utils.BRANCH_KEY = prefs.getString("branch_key", "");
        Utils.BRANCH_SECRET = prefs.getString("branch_secret", "");
        Retrofit retrofit = APIClient.getClient();
        service = retrofit.create(LinkHelper.class);
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
    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }
}
