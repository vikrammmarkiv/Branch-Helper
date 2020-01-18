package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.branchiohelper.adapter.FormAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LinkCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_create);
        RecyclerView recyclerView = findViewById(R.id.editrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<String> formData = new ArrayList<>();
        formData.add("standard");
        FormAdapter formAdapter = new FormAdapter(formData);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setAdapter(formAdapter);

        findViewById(R.id.add_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formAdapter.mFormState.add("standard");
                formAdapter.notifyItemInserted(formAdapter.mFormState.size());
                recyclerView.smoothScrollToPosition(formAdapter.mFormState.size());
            }
        });
    }
    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
