package com.miaxis.smartbank.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.index.CustomerActivity;
import com.miaxis.smartbank.adapter.CustomerAdapter;
import com.miaxis.smartbank.adapter.NoticeAdapter;
import com.miaxis.smartbank.domain.Customer;
import com.miaxis.smartbank.domain.Notice;
import com.miaxis.smartbank.domain.event.NotifyEvent;
import com.miaxis.smartbank.utils.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    @ViewInject(R.id.tv_middle)
    private TextView tv_middle;

    @ViewInject(R.id.rv_notice)
    private RecyclerView rv_notice;

    @ViewInject(R.id.rv_customer)
    private RecyclerView rv_customer;

    private NoticeAdapter noticeAdapter;
    private CustomerAdapter customerAdapter;

    private List<Notice> noticeList;
    private List<Customer> customerList;


    public IndexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_index, container, false);
        x.view().inject(this, v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initView();

    }

    private void initData() {
        noticeList = new ArrayList<>();
        customerList = new ArrayList<>();

        Notice n = new Notice();
        n.setTitle("1号窗口，请求援助");
        n.setOptime(DateUtil.toHourMinString(new Date()));

        Notice n2 = new Notice();
        n2.setTitle("2号窗口，差评");
        n2.setOptime(DateUtil.toHourMinString(new Date()));

        noticeList.add(n);
        noticeList.add(n2);

        noticeAdapter = new NoticeAdapter(noticeList, getContext());

        Customer c = new Customer();
        c.setCustomname("张三");
        c.setBusiness("贵宾业务");
        c.setComeDate(DateUtil.toMonthDay(new Date()));
        c.setComeTime(DateUtil.toHourMinString(new Date()));

        customerList.add(c);
        customerAdapter = new CustomerAdapter(customerList, getContext());

    }

    private void initView() {
        tv_middle.setText("通知");
        rv_notice.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_notice.setAdapter(noticeAdapter);
        noticeAdapter.setNoticeListener(new NoticeAdapter.NoticeListener() {
            @Override
            public void onItemClick(View view, int position) {
                noticeList.get(position).setRead(true);
                noticeAdapter.notifyDataSetChanged();
            }
        });

        rv_customer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_customer.setAdapter(customerAdapter);
        customerAdapter.setListener(new CustomerAdapter.CustomerListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                intent.putExtra("customer", customerList.get(position));
                startActivity(intent);
            }
        });

    }

    @Event(R.id.fab_list)
    private void listLayout(View view) {
        rv_notice.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Event(R.id.fab_grid)
    private void gridLayout(View view) {
        rv_notice.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNotifyEvent(NotifyEvent event) {
        Log.e("----","onMessageArrivefffff");
        Toast.makeText(getContext(), event.getTopic()+"-"+event.getContent(), Toast.LENGTH_SHORT).show();
        inform();
    }

    //发出提醒， 震动， 声音
    private void inform(){
        //震动
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern,-1);
        beep();
        //
    }

    /**
     * 提示音
     */
    private void beep(){
        NotificationManager manger = (NotificationManager)
               getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.defaults=Notification.DEFAULT_SOUND;
        manger.notify(1, notification);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
