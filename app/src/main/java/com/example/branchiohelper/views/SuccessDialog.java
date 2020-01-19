package com.example.branchiohelper.views;

/* Created by Vikram on 19-01-2020. */

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.example.branchiohelper.R;

import java.util.Objects;

public class SuccessDialog {
    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.success_dialog);

        TextView text = dialog.findViewById(R.id.generated_link);
        text.setText(msg);

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
