package com.example.branchiohelper.adapter;

/* Created by Vikram on 26-01-2020. */

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.Collections;

public class CustomArrayAdapter extends ArrayAdapter {


    public CustomArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        Log.d("DROPDOWN@@",String.valueOf(super.getCount()));
        return super.getCount();
    }


}
