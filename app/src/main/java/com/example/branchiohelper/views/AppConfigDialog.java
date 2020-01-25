package com.example.branchiohelper.views;

/* Created by Vikram on 19-01-2020. */

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.branchiohelper.MainActivity;
import com.example.branchiohelper.R;
import com.example.branchiohelper.constants.Constants;
import com.example.branchiohelper.utils.Utils;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class AppConfigDialog {
    private LinearLayout save;
    private LinearLayout capture;
    private EditText branch_key;
    private EditText branch_secret;
    private Dialog dialog;
    public AppConfigDialog(MainActivity activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.app_config_dialog);

        branch_key = dialog.findViewById(R.id.branch_key);
        branch_secret = dialog.findViewById(R.id.branch_secret);

        if (!Utils.BRANCH_KEY.equals(""))
            branch_key.setText(Utils.BRANCH_KEY);
        if (!Utils.BRANCH_SECRET.equals(""))
            branch_secret.setText(Utils.BRANCH_SECRET);
        capture = dialog.findViewById(R.id.camera_button);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.captureImage();
            }
        });
        save = dialog.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {SharedPreferences.Editor editor = activity.getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE).edit();
                String key = branch_key.getText().toString();
                String secret = branch_secret.getText().toString();
                editor.putString("branch_key", key);
                editor.putString("branch_secret", secret);
                editor.apply();
                dialog.dismiss();
                Utils.showSnackBar(activity.findViewById(R.id.cl_main_activity), "App Saved!");
                Utils.BRANCH_KEY=key;
                Utils.BRANCH_SECRET=secret;
            }
        });
    }
    public void showDialog(){
        dialog.show();
    }
}
