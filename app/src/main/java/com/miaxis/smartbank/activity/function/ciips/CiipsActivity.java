package com.miaxis.smartbank.activity.function.ciips;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.view.ConfirmDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_ciips)
public class CiipsActivity extends BaseActivity {

    @ViewInject(R.id.tv_left)
    private TextView tv_left;

    @ViewInject(R.id.tv_middle)
    private TextView tv_middle;

    @ViewInject(R.id.wv_ciips)
    private WebView wv_ciips;

    private ConfirmDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

        wv_ciips.loadUrl("http://www.zzjrfw.cn/CIIPS_C/app/mobile/category.jsp");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_ciips.setWebViewClient(new WebViewClient(){
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
        WebSettings s = wv_ciips.getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setDomStorageEnabled(true);
        wv_ciips.requestFocus();

        dialog = new ConfirmDialog();
        dialog.setContent("您确定要退出吗？未保存的数据将会丢失");
        dialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        tv_left.setVisibility(View.VISIBLE);
        tv_middle.setText("预填单系统");
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        dialog.show(getFragmentManager(), "CONFIRM");
    }

    @Override
    public void onBackPressed() {

    }
}
