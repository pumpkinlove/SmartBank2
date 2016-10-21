package com.miaxis.smartbank.activity.function.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.adapter.ProductionAdapter;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.domain.Production;
import com.miaxis.smartbank.utils.CommonUtil;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.XUtil;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ContentView(R.layout.activity_production_list)
public class ProductionListActivity extends BaseActivity {

    private List<Production> productionList;
    private ProductionAdapter adapter;

    @ViewInject(R.id.rv_production)
    private RecyclerView rvProduction;

    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
        initView();
        downList(null);
    }

    @Override
    protected void initData() {
        productionList = new ArrayList<>();
        adapter = new ProductionAdapter(productionList, rvProduction);
    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setText("理财产品");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("同步");
        tv_right.setTextColor(getResources().getColor(R.color.green_dark));

        rvProduction.setLayoutManager(new LinearLayoutManager(this));
        rvProduction.setAdapter(adapter);
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        finish();
    }


    @Event(R.id.tv_right)
    private void downList(View view) {
        String url = MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" + Constant.PRODUCT_LIST;
        Map<String, Object> params = new HashMap<>();

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

                productionList.clear();

                JsonArray jsonArray = o.getAsJsonArray("rows");
                for (int i=0; i<jsonArray.size(); i++) {
                    JsonElement e = jsonArray.get(i);
                    Production p = g.fromJson(e, Production.class);
                    productionList.add(p);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("---",ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


}
