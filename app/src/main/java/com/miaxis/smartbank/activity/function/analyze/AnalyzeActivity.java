package com.miaxis.smartbank.activity.function.analyze;

import android.os.Bundle;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.activity_analyze)
public class AnalyzeActivity extends BaseActivity {

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
}
