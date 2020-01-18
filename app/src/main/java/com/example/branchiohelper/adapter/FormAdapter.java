package com.example.branchiohelper.adapter;

/* Created by Vikram on 19-01-2020. */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.branchiohelper.R;

import java.util.ArrayList;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {
    public ArrayList<String> mFormState;
    public FormAdapter(ArrayList<String> formState) {
        mFormState = formState;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.standard_data, parent, false);
        return new MyViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return mFormState.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView selector;
        EditText key, value;

        MyViewHolder(View view, int viewType) {
            super(view);
            switch (viewType) {
                case R.layout.standard_data:
                    selector = itemView.findViewById(R.id.form_mode);
                    key = itemView.findViewById(R.id.key);
                    value = itemView.findViewById(R.id.value);
            }
        }
    }
}