package com.miaxis.smartbank.activity.function.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.adapter.ProductionAdapter;
import com.miaxis.smartbank.domain.Production;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
        initView();
    }

    @Override
    protected void initData() {

        productionList = new ArrayList<>();
        Production p = new Production();
        p.setName("神奇理财1");
        p.setPicUrl("http://img2.imgtn.bdimg.com/it/u=2228090847,3776253819&fm=11&gp=0.jpg");
        p.setDescribe("神奇的理财产品");
        p.setTerm("这个理财产品好啊");
        productionList.add(p);
        Production p2 = new Production();
        p2.setName("神奇理财2");
        p2.setPicUrl("http://img3.imgtn.bdimg.com/it/u=1226791657,2480826386&fm=11&gp=0.jpg");
        p2.setDescribe("神奇的理财产品");
        p2.setTerm("这个理财产品好啊");
        productionList.add(p2);
        Production p3 = new Production();
        p3.setName("神奇理财3");
        p3.setPicUrl("http://img5.imgtn.bdimg.com/it/u=2088999013,705617920&fm=23&gp=0.jpg");
        p3.setDescribe("神奇的理财产品");
        p3.setTerm("这个理财产品好啊");
        productionList.add(p3);
        adapter = new ProductionAdapter(productionList, rvProduction);
    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setText("理财产品");

        rvProduction.setLayoutManager(new LinearLayoutManager(this));
        rvProduction.setAdapter(adapter);
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        finish();
    }

    private void downList() {
        String url = "";
        Map<String, Object> params = new HashMap<>();

        XUtil.Post(url, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
