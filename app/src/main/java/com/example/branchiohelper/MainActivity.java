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
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.branchiohelper.constants.Constants.CAMERA_PERMISSION_CODE;
import static com.example.branchiohelper.constants.Constants.CAMERA_REQUEST;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    String branch_key;
    LinkHelper service;
    Retrofit retrofit;
    ImageView settings;
    private CardView linkCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = APIClient.getClient();
        service = retrofit.create(LinkHelper.class);
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
        SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);
        Utils.BRANCH_KEY = prefs.getString("branch_key", "");
        Utils.BRANCH_SECRET = prefs.getString("branch_secret", "");
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
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ArrayList<String> imageTextList = Utils.scanImageForText(photo, this);
            StringBuilder str = new StringBuilder();
            if(imageTextList.size()>0) {
                for (String s : imageTextList)
                    str.append(s);
                Log.d("branch image::",str.toString());
                Utils.showSnackBar(findViewById(R.id.app_header), str.toString());
            }
            Log.d("branch image::","4353534");

        }
    }

    public void captureImage(){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, Constants.CAMERA_PERMISSION_CODE);
            Log.d("Branch LLL","requested");
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST);
        }
    }
}
