package com.example.branchiohelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.branchiohelper.constants.Constants.CAMERA_PERMISSION_CODE;
import static com.example.branchiohelper.constants.Constants.CAMERA_REQUEST;

public class MainActivity extends AppCompatActivity {
    public static LinkHelper service;
    TextView responseText;
    String branch_key;
    Retrofit retrofit;
    ImageView settings;
    String filename;
    Uri imageUri;
    AppConfigDialog dialog;

    @OnClick(R.id.link_create) void createLink(){
        startActivity(new Intent(this,LinkCreateActivity.class));
    }

    @OnClick(R.id.link_read) void readLink(){
        startActivity(new Intent(this,LinkReadActivity.class));
    }

    @OnClick(R.id.link_update) void updateLink(){
        startActivity(new Intent(this,LinkUpdateActivity.class));
    }

    @OnClick(R.id.link_delete) void deleteLink(){
        startActivity(new Intent(this,LinkDeleteActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = APIClient.getClient();
        responseText = findViewById(R.id.response);
        settings = findViewById(R.id.settings);

        init();

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AppConfigDialog(MainActivity.this);
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

    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                captureImage();
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d("CAMERAA",contents);
                storeScanResults(contents);

        }
    }

    public void captureImage(){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.CAMERA_PERMISSION_CODE);
            Log.d("Branch LLL","requested");
        }
        else
        {
            try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                dialog.dismissDialog();
                startActivityForResult(intent, CAMERA_REQUEST);

            } catch (Exception e) {

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);

            }
        }
    }
    void storeScanResults(String scanResult){
        dialog = new AppConfigDialog(MainActivity.this);
        dialog.showDialog();
        String[] results = scanResult.split("\n");
        for (String str:results){
            if (str.contains("key")){
                dialog.setKey(str);
            }
            else if (str.contains("secret")){
                dialog.setSecret(str);
            }
        }
    }
}
