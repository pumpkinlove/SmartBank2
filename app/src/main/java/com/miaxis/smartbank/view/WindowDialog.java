package com.miaxis.smartbank.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.WindowInfo;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/9/9.
 */
public class WindowDialog extends DialogFragment {

    @ViewInject(R.id.d_window_no)
    private TextView tvWindowNo;
    @ViewInject(R.id.d_window_worker_name)
    private TextView tvWorkerName;
    @ViewInject(R.id.d_window_type)
    private TextView tvWindowType;
    @ViewInject(R.id.d_window_good)
    private TextView tvGood;
    @ViewInject(R.id.d_window_bad)
    private TextView tvBad;
    @ViewInject(R.id.d_window_status)
    private TextView tvStatus;
    @ViewInject(R.id.d_window_average_time)
    private TextView tvAverageTime;

    private WindowInfo windowInfo;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_window, container);
        x.view().inject(this, view);

        initData();
        initView();

        return view;
    }

    private void initData() {
        windowInfo = (WindowInfo) getArguments().getSerializable("windowInfo");
    }

    private void initView() {
        if(windowInfo == null) {
            return;
        }
        tvWindowNo.setText(windowInfo.getWindowNo() + " 号窗口");
        tvWorkerName.setText(windowInfo.getWorkerName());
        tvWindowType.setText(windowInfo.getBusiType());
        tvGood.setText(windowInfo.getGood());
        tvBad.setText(windowInfo.getBad());
        tvStatus.setText(windowInfo.getStatus());
        tvAverageTime.setText(windowInfo.getAverageBusiTime());
    }
}
