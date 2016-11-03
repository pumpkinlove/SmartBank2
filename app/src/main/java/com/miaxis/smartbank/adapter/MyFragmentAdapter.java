package com.miaxis.smartbank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.smartbank.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentLis) {
        super(fm);
        this.fragmentList = fragmentLis;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if(fragmentList == null){
            return 0;
        }
        return fragmentList.size();
    }

}
