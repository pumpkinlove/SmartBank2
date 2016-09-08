package com.miaxis.smartbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.miaxis.smartbank.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

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

    }

    @Event(R.id.tv_login)
    private void login(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
