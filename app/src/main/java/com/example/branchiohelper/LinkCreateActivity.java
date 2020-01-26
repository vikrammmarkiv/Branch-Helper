package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;

import com.example.branchiohelper.adapter.FormAdapter;
import com.example.branchiohelper.events.LinkCreatedEvent;
import com.example.branchiohelper.models.FormData;
import com.example.branchiohelper.utils.LinkUtils;
import com.example.branchiohelper.utils.Utils;
import com.example.branchiohelper.views.SuccessDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class LinkCreateActivity extends AppCompatActivity {

    View loading_screen;
    Boolean quickLinkEnabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_create);
        RecyclerView recyclerView = findViewById(R.id.editrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<FormData> formItems = new ArrayList<>();
        formItems.add(new FormData());
        FormAdapter formAdapter = new FormAdapter(formItems, this, recyclerView);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setAdapter(formAdapter);

        loading_screen = findViewById(R.id.loading_screen);

        findViewById(R.id.add_data).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                formAdapter.mFormItems.add(new FormData());
                formAdapter.notifyItemInserted(formAdapter.mFormItems.size());
                recyclerView.smoothScrollToPosition(formAdapter.mFormItems.size());
                view.performClick();
                return false;
            }
        });

        findViewById(R.id.submit_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(LinkCreateActivity.this);
                loading_screen.setVisibility(View.VISIBLE);
                quickLinkEnabled = ((CheckBox)findViewById(R.id.quick_link)).isChecked();
                LinkUtils.createLink(MainActivity.service,Utils.prepareLinkData(formAdapter.mFormItems, quickLinkEnabled,false));
            }
        });
    }
    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    private void showSuccessDialog(String link){
        SuccessDialog dialog = new SuccessDialog();
        dialog.showDialog(this,link);
    }
    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe()
    public void onLinkCreatedEvent(LinkCreatedEvent event) {
        loading_screen.setVisibility(View.GONE);
        showSuccessDialog(event.getResponse().getUrl());
    }
}
