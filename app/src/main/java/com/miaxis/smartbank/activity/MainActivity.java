package com.miaxis.smartbank.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.adapter.MyFragmentAdapter;
import com.miaxis.smartbank.fragment.HomeFragment;
import com.miaxis.smartbank.fragment.FunctionFragment;
import com.miaxis.smartbank.fragment.IndexFragment;
import com.miaxis.smartbank.fragment.TeamFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.tl_main)
    private TabLayout tl_main;
    @ViewInject(R.id.vp_main)
    private ViewPager vp_main;

    private MyFragmentAdapter adapter;
    private List<Fragment> fragmentList;
    private IndexFragment indexFragment;
    private TeamFragment teamFragment;
    private FunctionFragment functionFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initTabLayout();
    }

    @Override
    protected void initData() {

        indexFragment = new IndexFragment();
        teamFragment = new TeamFragment();
        functionFragment = new FunctionFragment();
        homeFragment = new HomeFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(indexFragment);
        fragmentList.add(teamFragment);
        fragmentList.add(functionFragment);
        fragmentList.add(homeFragment);

        adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragmentList);

    }

    @Override
    protected void initView() {
        x.view().inject(this);
        vp_main.setAdapter(adapter);
        vp_main.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_main));
        tl_main.setupWithViewPager(vp_main);
        vp_main.setOffscreenPageLimit(20);

    }

    private void initTabLayout(){

        List<Drawable> drawableList = new ArrayList<>();
        Drawable tab_drawable = getResources().getDrawable(R.drawable.tab1_n);
        tab_drawable.setTint(getResources().getColor(R.color.white));
        drawableList.add(tab_drawable);
        tab_drawable = getResources().getDrawable(R.drawable.tab2_n);
        tab_drawable.setTint(getResources().getColor(R.color.white));
        drawableList.add(tab_drawable);
        tab_drawable = getResources().getDrawable(R.drawable.tab3_n);
        tab_drawable.setTint(getResources().getColor(R.color.white));
        drawableList.add(tab_drawable);
        tab_drawable = getResources().getDrawable(R.drawable.tab4_n);
        tab_drawable.setTint(getResources().getColor(R.color.white));
        drawableList.add(tab_drawable);

        for(int i=0; i< tl_main.getTabCount(); i++){
            final TabLayout.Tab tab = tl_main.getTabAt(i);
            if(tab != null){
//                tab.setText(titles[i]);
                tab.setIcon(drawableList.get(i));

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
