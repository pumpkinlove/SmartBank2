package com.miaxis.smartbank.activity.function.tool;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.adapter.MyFragmentAdapter;
import com.miaxis.smartbank.fragment.tools.FinanceFragment;
import com.miaxis.smartbank.fragment.tools.LoanFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_tools)
public class ToolsActivity extends BaseActivity {

    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    @ViewInject(R.id.tl_tool)
    private TabLayout toolTabLayout;

    @ViewInject(R.id.vp_tool)
    private ViewPager toolViewPager;

    private MyFragmentAdapter adapter;
    private List<Fragment> fragmentList;

    private FinanceFragment financeFragment;
    private LoanFragment loanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();
        initTabLayout();
    }

    @Override
    protected void initData() {
        financeFragment = new FinanceFragment();
        loanFragment = new LoanFragment();
        fragmentList = new ArrayList<>();

        fragmentList.add(financeFragment);
        fragmentList.add(loanFragment);

        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList);
    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setText("工具");
        toolViewPager.setAdapter(adapter);
        toolViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolTabLayout));
        toolTabLayout.setupWithViewPager(toolViewPager);
        toolViewPager.setOffscreenPageLimit(10);
    }

    private void initTabLayout() {

        String[] titles = {"理财计算器","贷款计算器"};

        for (int i=0; i< toolTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = toolTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(titles[i]);
            }
        }
    }

    @Event(R.id.tv_left)
    private void back(View view) {
        finish();
    }
}
