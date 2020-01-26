package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.branchiohelper.adapter.FormAdapter;
import com.example.branchiohelper.events.LinkReadEvent;
import com.example.branchiohelper.events.LinkUpdatedEvent;
import com.example.branchiohelper.models.FormData;
import com.example.branchiohelper.utils.LinkUtils;
import com.example.branchiohelper.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LinkUpdateActivity extends AppCompatActivity {

    FormAdapter formAdapter;

    @BindView(R.id.editrecyclerview)
    RecyclerView updateDataRecyclerView;

    @BindView(R.id.link_read_value)
    EditText linkToRead;

    @BindView(R.id.link_read_data)
    TextView linkData;

    @BindView(R.id.loading_screen)
    View loadingScreen;

    @BindView(R.id.add_update_data)
    View addupdateData;

    @BindView(R.id.form_data)
    View formData;

    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    @BindView(R.id.submit_form)
    View submitButton;

    @OnClick(R.id.refresh_button) void refresh(){
        startActivity(new Intent(this,LinkUpdateActivity.class));
        finish();
    }


    @OnClick(R.id.submit_form) void submit(){
        loadingScreen.setVisibility(View.VISIBLE);
        LinkUtils.updateLink(MainActivity.service,linkToRead.getText().toString(),Utils.prepareLinkData(formAdapter.mFormItems,false,true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_update);
        init();
    }
    void init(){
        ButterKnife.bind(this);
        updateDataRecyclerView = findViewById(R.id.editrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        updateDataRecyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<FormData> formDataList = new ArrayList<>();
        formDataList.add(new FormData());
        formAdapter = new FormAdapter(formDataList, this, updateDataRecyclerView);
        updateDataRecyclerView.setItemViewCacheSize(20);
        updateDataRecyclerView.setAdapter(formAdapter);

        addupdateData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                formAdapter.mFormItems.add(new FormData());
                formAdapter.notifyItemInserted(formAdapter.mFormItems.size());
                updateDataRecyclerView.smoothScrollToPosition(formAdapter.mFormItems.size());
                view.performClick();
                return false;
            }
        });
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
    public void onLinkUpdateEvent(LinkUpdatedEvent event) {
        updateDataRecyclerView.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        bottomSheet.setVisibility(View.GONE);
        linkData.setText(Utils.formatJsonString(event.getResponse()));
        formData.setVisibility(View.VISIBLE);
        loadingScreen.setVisibility(View.GONE);

    }

    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
