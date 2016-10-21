package com.miaxis.smartbank.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.adapter.MyFragmentAdapter;
import com.miaxis.smartbank.domain.event.MessageArrivedEvent;
import com.miaxis.smartbank.fragment.HomeFragment;
import com.miaxis.smartbank.fragment.FunctionFragment;
import com.miaxis.smartbank.fragment.IndexFragment;
import com.miaxis.smartbank.fragment.TeamFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
        EventBus.getDefault().register(this);
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
            TabLayout.Tab tab = tl_main.getTabAt(i);
            if(tab != null){
//                tab.setText(titles[i]);
                tab.setIcon(drawableList.get(i));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageArrive(MessageArrivedEvent event) {
        Log.e("----","onMessageArrive");
        Toast.makeText(this, event.getTopic()+"-"+event.getMessage(), Toast.LENGTH_SHORT).show();
        inform();
    }

    //发出提醒， 震动， 声音
    private void inform(){
        //震动
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern,-1);
        beep();
        //
    }

    /**
     * 提示音
     */
    private void beep(){
        NotificationManager manger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.defaults=Notification.DEFAULT_SOUND;
        manger.notify(1, notification);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
