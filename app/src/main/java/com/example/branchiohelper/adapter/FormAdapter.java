package com.example.branchiohelper.adapter;

/* Created by Vikram on 19-01-2020. */

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.branchiohelper.R;
import com.example.branchiohelper.models.FormData;

import java.util.ArrayList;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {
    public ArrayList<FormData> mFormItems;
    public FormAdapter(ArrayList<FormData> formItems) {
        mFormItems = formItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.standard_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mFormItems.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView selector;
        EditText form_key, form_value;

        MyViewHolder(View view) {
            super(view);
            selector = view.findViewById(R.id.form_mode);
            form_key = view.findViewById(R.id.key);
            form_value = view.findViewById(R.id.value);

            form_key.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mFormItems.get(getAdapterPosition()).setKey(charSequence+"");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            form_value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mFormItems.get(getAdapterPosition()).setValue(charSequence+"");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }


    }

}