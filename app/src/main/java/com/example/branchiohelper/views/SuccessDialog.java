package com.example.branchiohelper.views;

/* Created by Vikram on 19-01-2020. */

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.branchiohelper.R;
import com.example.branchiohelper.utils.Utils;

import java.util.Objects;

public class SuccessDialog {
    private LinearLayout copy;
    private LinearLayout share;
    public void showDialog(Activity activity, String data){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.success_dialog);

        TextView text = dialog.findViewById(R.id.generated_link);

        text.setText(data);

        copy = dialog.findViewById(R.id.copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", data);
                clipboard.setPrimaryClip(clip);
                Utils.showSnackBar(activity.findViewById(R.id.header), "Link Copied!");
            }
        });

        share = dialog.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, data);
                intent.setType("text/plain");
                activity.startActivity(intent);
            }
        });
//        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();

    }
}
