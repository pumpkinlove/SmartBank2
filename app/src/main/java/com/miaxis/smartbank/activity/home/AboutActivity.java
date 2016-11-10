package com.miaxis.smartbank.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity {

    @ViewInject(R.id.tv_middle)
    private TextView tv_middle;

    @ViewInject(R.id.tv_left)
    private TextView tv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_left.setVisibility(View.VISIBLE);
        tv_middle.setText("关于");
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        finish();
    }
}
