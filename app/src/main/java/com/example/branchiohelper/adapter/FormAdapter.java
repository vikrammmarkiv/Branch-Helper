package com.example.branchiohelper.adapter;

/* Created by Vikram on 19-01-2020. */

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.branchiohelper.R;
import com.example.branchiohelper.constants.Constants;
import com.example.branchiohelper.models.FormData;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {
    public ArrayList<FormData> mFormItems;
    public Activity mActivity;
    public FormAdapter(ArrayList<FormData> formItems, Activity activity) {
        mFormItems = formItems;
        mActivity = activity;
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
        EditText form_value;
        AutoCompleteTextView form_key;

        int dropDownHeightMax = (int) mActivity.getResources().getDimension(R.dimen._100sdp);

        MyViewHolder(View view) {
            super(view);
            selector = view.findViewById(R.id.form_mode);
            form_key = view.findViewById(R.id.key);
            form_value = view.findViewById(R.id.value);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(mActivity,R.layout.autocomplete_layout, Constants.BRANCH_STANDARD_PARAMS);
            form_key.setAdapter(adapter);
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
                    if (adapter.getCount()<=1){
                        form_key.setDropDownHeight(WRAP_CONTENT);
                    }
                    else form_key.setDropDownHeight(dropDownHeightMax);
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