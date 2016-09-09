package com.miaxis.smartbank.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.adapter.QueueAdapter;
import com.miaxis.smartbank.adapter.WindowAdapter;
import com.miaxis.smartbank.domain.QueueInfo;
import com.miaxis.smartbank.domain.WindowInfo;
import com.miaxis.smartbank.view.WindowDialog;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {

    @ViewInject(R.id.tv_middle)
    private TextView tv_middle;

    @ViewInject(R.id.rv_queue)
    private RecyclerView rv_queue;
    private QueueAdapter queueAdapter;
    private List<QueueInfo> queueInfoList;

    @ViewInject(R.id.rv_window)
    private RecyclerView rv_window;
    private WindowAdapter windowAdapter;
    private List<WindowInfo> windowInfoList;

    private WindowDialog windowDialog;

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_team, container, false);
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

        windowDialog = new WindowDialog();

        queueInfoList = new ArrayList<>();
        QueueInfo q1 = new QueueInfo();
        q1.setTypeName("个人业务");
        q1.setCurNum("A001");
        q1.setNextNum("A002");
        q1.setWaitNum("13");
        QueueInfo q2 = new QueueInfo();
        q2.setTypeName("贵宾业务");
        q2.setCurNum("B001");
        q2.setNextNum("B002");
        q2.setWaitNum("3");
        queueInfoList.add(q1);
        queueInfoList.add(q2);
        queueAdapter = new QueueAdapter(queueInfoList, getContext());

        windowInfoList = new ArrayList<>();
        WindowInfo w1 = new WindowInfo();
        w1.setWindowNo("1");
        w1.setWorkerName("柜员甲");
        w1.setBusiType("个人业务");
        w1.setAverageBusiTime("10分钟");
        w1.setStatus("A001 办理中");
        windowInfoList.add(w1);
        windowAdapter = new WindowAdapter(windowInfoList, getContext());
        windowAdapter.setOnItemClickListener(new WindowAdapter.WindowListener() {
            @Override
            public void onItemClick(View view, int position) {
                WindowInfo windowInfo = windowInfoList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("windowInfo", windowInfo);
                windowDialog.setArguments(bundle);
                windowDialog.show(getActivity().getFragmentManager(), "windowInfo");
            }
        });
    }

    private void initView() {
        tv_middle.setText("观察");
        rv_queue.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_queue.setAdapter(queueAdapter);

        rv_window.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_window.setAdapter(windowAdapter);
    }

}
