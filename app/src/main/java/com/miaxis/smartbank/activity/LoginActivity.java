package com.miaxis.smartbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.miaxis.smartbank.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.icvp)
    private HorizontalInfiniteCycleViewPager icvp;

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

//        icvp.setAdapter();
//        icvp.setScrollDuration(500);
//        icvp.setInterpolator(...);
//        icvp.setMediumScaled(true);
//        icvp.setMaxPageScale(0.8F);
//        icvp.setMinPageScale(0.5F);
//        icvp.setCenterPageScaleOffset(30.0F);
//        icvp.setMinPageScaleOffset(5.0F);
//        icvp.setOnInfiniteCyclePageTransformListener(...);

    }

    @Event(R.id.tv_login)
    private void login(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
