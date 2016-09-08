package com.miaxis.smartbank.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.adapter.NoticeAdapter;
import com.miaxis.smartbank.domain.Notice;
import com.miaxis.smartbank.utils.DateUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    @ViewInject(R.id.rv_notice)
    private RecyclerView rv_notice;

    private NoticeAdapter noticeAdapter;

    private List<Notice> noticeList;

    public IndexFragment() {
        // Required empty public constructor
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

    private void initData(){
        noticeList = new ArrayList<>();

        Notice n = new Notice();
        n.setTitle("1号窗口，请求援助");
        n.setOptime(DateUtil.toHourMinString(new Date()));

        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);
        noticeList.add(n);

        noticeAdapter = new NoticeAdapter(noticeList, getContext());
    }

    private void initView() {
        rv_notice.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rv_notice.setAdapter(noticeAdapter);
        noticeAdapter.setNoticeListener(new NoticeAdapter.NoticeListener() {
            @Override
            public void onItemClick(View view, int position) {
                noticeList.get(position).setRead(true);
                noticeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void listLayout(View view) {

    }

}
