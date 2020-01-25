package com.example.branchiohelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.branchiohelper.events.LinkCreatedEvent;
import com.example.branchiohelper.events.LinkReadEvent;
import com.example.branchiohelper.utils.LinkUtils;
import com.example.branchiohelper.utils.Utils;
import com.example.branchiohelper.views.SuccessDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.branchiohelper.utils.LinkUtils.convertLinkData;

public class LinkReadActivity extends AppCompatActivity {

    @BindView(R.id.link_read_value)
    EditText linkToRead;

    @BindView(R.id.link_read_data)
    TextView linkData;

    @BindView(R.id.loading_screen)
    View loadingScreen;

    @OnClick(R.id.submit_form) void submit(){
        Utils.hideKeyboard(this);
        loadingScreen.setVisibility(View.VISIBLE);
        LinkUtils.readLink(MainActivity.service,linkToRead.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_read);
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
    public void onLinkReadEvent(LinkReadEvent event) {
        loadingScreen.setVisibility(View.GONE);
        linkData.setText(Utils.formatJsonString(event.getResponse().toString()));
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
