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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

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
        p.setName("神奇理财");
        productionList.add(p);
        adapter = new ProductionAdapter(productionList, this, rvProduction);
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
}
