package com.miaxis.smartbank.activity.function;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_ciips)
public class CiipsActivity extends BaseActivity {

    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    @ViewInject(R.id.wv_ciips)
    private WebView wvCiips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

        wvCiips.loadUrl("http://www.zzjrfw.cn/CIIPS_C/app/mobile/category.jsp");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wvCiips.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setText("预填单系统");
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        finish();
    }
}
