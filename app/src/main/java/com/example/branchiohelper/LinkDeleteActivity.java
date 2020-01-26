package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.example.branchiohelper.events.LinkDeleteEvent;
import com.example.branchiohelper.events.LinkReadEvent;
import com.example.branchiohelper.utils.LinkUtils;
import com.example.branchiohelper.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LinkDeleteActivity extends AppCompatActivity {

    @BindView(R.id.loading_screen)
    View loadingScreen;

    @BindView(R.id.link_to_delete)
    EditText linkToDelete;

    @BindView(R.id.link_deleted_status)
    View linkDeleted;

    @OnClick(R.id.submit_form)void submit(){
        Utils.hideKeyboard(this);
        loadingScreen.setVisibility(View.VISIBLE);
        LinkUtils.deleteLink(MainActivity.service,linkToDelete.getText().toString(),Utils.prepareLinkData(null,false,true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_delete);
        ButterKnife.bind(this);
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
    public void onLinkDeleteEvent(LinkDeleteEvent event) {
        loadingScreen.setVisibility(View.GONE);
        if (!event.getResponse().equals("true"))
            return;
        linkDeleted.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linkDeleted.setVisibility(View.GONE);
            }
        },3000);
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
