package com.miaxis.smartbank.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.function.CiipsActivity;
import com.miaxis.smartbank.activity.function.doing.BankDoingActivity;
import com.miaxis.smartbank.activity.function.doing.NewDoingActivity;
import com.miaxis.smartbank.activity.function.tool.ToolsActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionFragment extends Fragment {

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    public FunctionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_function, container, false);
        x.view().inject(this, v);
        initView();
        return v;
    }

    private void initView() {
        tvMiddle.setText("功能");
    }

    @Event(R.id.ll_new_doing)
    private void newDoing(View view) {
        startActivity(new Intent(getActivity(), NewDoingActivity.class));
    }

    @Event(R.id.tv_func_tools)
    private void tools(View view) {
        startActivity(new Intent(getActivity(), ToolsActivity.class));
    }

    @Event(R.id.tv_func_ciips)
    private void ciips(View view) {
        startActivity(new Intent(getActivity(), CiipsActivity.class));
    }

    @Event(R.id.tv_func_bank_doing)
    private void bankDoing(View view) {
        startActivity(new Intent(getActivity(), BankDoingActivity.class));
    }
}
