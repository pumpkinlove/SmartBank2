package com.miaxis.smartbank.activity.function.doing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_preview_photo)
public class PreviewPhotoActivity extends BaseActivity {

    @ViewInject(R.id.iv_preview_photo)
    private ImageView iv_preview_photo;

    @ViewInject(R.id.tv_left)
    private TextView tv_left;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    private String url;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        bitmap = getIntent().getParcelableExtra("bitmap");
    }

    @Override
    protected void initView() {

        tv_left.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("删除");
        x.image().bind(iv_preview_photo, url);
        iv_preview_photo.setImageBitmap(bitmap);



    }

    @Override
    public void finish() {

        super.finish();
    }

    @Event(R.id.tv_left)
    private void back(View view) {
        finish();
    }
}
