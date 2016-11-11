package com.miaxis.smartbank.activity.function.doing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.adapter.BankDoingAdapter;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.BankDoing;
import com.miaxis.smartbank.utils.CommonUtil;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.XUtil;
import com.miaxis.smartbank.view.ImageDialog;
import com.miaxis.smartbank.view.XListView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ContentView(R.layout.activity_bank_doing)
public class BankDoingActivity extends BaseActivity implements XListView.IXListViewListener {

    private int page = 1;
    private boolean isClear;
    private int total = 0;

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

    @ViewInject(R.id.tv_right)
    private TextView tvRight;

    @ViewInject(R.id.xlv_bank_doing)
    private XListView xlvBankDoing;

    private List<BankDoing> bankDoingList;

    private BankDoingAdapter adapter;

    private ImageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();
        refreshList();
    }

    @Override
    protected void initData() {
        bankDoingList = new ArrayList<>();
        dialog = new ImageDialog();

        adapter = new BankDoingAdapter(bankDoingList, this);
        adapter.setPhotoClickListener(new BankDoingAdapter.PhotoClickListener() {
            @Override
            public void onPhotoClick(String url) {
                dialog.setUrl(url);
                dialog.show(getFragmentManager(), "IMG_DIALOG"+ new Date().getTime());
            }
        });

        xlvBankDoing.setAdapter(adapter);

    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setText("网点活动");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(" + ");
        tvRight.setTextSize(25.0f);

        xlvBankDoing.setPullLoadEnable(true);
        xlvBankDoing.setPullRefreshEnable(true);
        xlvBankDoing.setXListViewListener(this);
        xlvBankDoing.setAdapter(adapter);
    }

    private void refreshList() {
        String url = MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" + Constant.DOING_LIST;

        Map<String, Object> params = new HashMap<>();

        params.put("page", page);
        params.put("rows", 10);

        XUtil.Post(url, params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e("---",result);
                Gson g = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);
                JsonObject o = element.getAsJsonObject();
                boolean success = o.get("success").getAsBoolean();
                if (!success) {
                    CommonUtil.alert(getFragmentManager(), "与服务器通讯失败");
                    return;
                }

                if (isClear) {
                    bankDoingList.clear();
                }

                JsonArray jsonArray = o.getAsJsonArray("rows");
                total = o.get("total").getAsInt();
                if (total <= bankDoingList.size()) {
                    Toast.makeText(getApplication(), "已经加载到最后一条", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0; i<jsonArray.size(); i++) {
                    JsonElement e = jsonArray.get(i);
                    BankDoing d = g.fromJson(e, BankDoing.class);
                    bankDoingList.add(d);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                xlvBankDoing.stopRefresh();
                xlvBankDoing.stopLoadMore();
                xlvBankDoing.setRefreshTime("刚刚");
            }
        });
    }


    @Override
    public void onRefresh() {
        isClear = true;
        page = 1;
        refreshList();
    }

    @Override
    public void onLoadMore() {
        isClear = false;
        page ++;
        refreshList();
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        finish();
    }

    @Event(R.id.tv_right)
    private void newDoingClick(View view) {
        startActivityForResult(new Intent(this, NewDoingActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1 :
                if (requestCode == RESULT_OK) {
                    refreshList();
                }
        }
    }
}
