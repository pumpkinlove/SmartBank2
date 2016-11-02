package com.miaxis.smartbank.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.home.ConfigActivity;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.Version;
import com.miaxis.smartbank.utils.CommonUtil;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.XUtil;
import com.miaxis.smartbank.view.AboutDialog;
import com.miaxis.smartbank.view.UpdateDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @ViewInject(R.id.tv_middle)
    private TextView tv_middle;

    private ProgressDialog pd_check_version;
    private UpdateDialog updateDialog;
    private AboutDialog aboutDialog;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
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
        aboutDialog = new AboutDialog();
        updateDialog = new UpdateDialog();
        pd_check_version = new ProgressDialog(getContext());
    }

    private void initView() {
        tv_middle.setText("我");

        pd_check_version.setMessage("正在检查更新...");
        pd_check_version.setCanceledOnTouchOutside(false);
    }

    @Event(R.id.ll_about)
    private void about(View view) {
        aboutDialog.show(getActivity().getFragmentManager(), "ABOUT");
    }

    @Event(R.id.ll_config)
    private void config(View view) {
        startActivity(new Intent(getActivity(), ConfigActivity.class));
    }

    @Event(R.id.tv_update)
    private void checkVersion(View view) {
        Log.e("---------","checkVersion");

        pd_check_version.show();
        String urlDemo = "http://" + MyApplication.config.getIp() + ":"
                + MyApplication.config.getPort() + "/"
                + Constant.PROJECT_NAME + "/"
                + Constant.CHECK_VERSION
                + "?organid=1001&versiontype=03";
        XUtil.Post(urlDemo, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("---------","onSuccess");
                Gson g = new Gson();

                Version lastVersion = g.fromJson(result, Version.class);

                try {
                    Version curVersion = CommonUtil.getCurVersion(getContext());
                    if (lastVersion.getVersionCode() > curVersion.getVersionCode() ) {
                        updateDialog.setLastVersion(lastVersion);
                        updateDialog.show(getActivity().getFragmentManager(),"update_dialog");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CommonUtil.alert(getActivity().getFragmentManager(), "升级失败");
                Log.e("---------","onError"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("---------","onCancelled");
            }

            @Override
            public void onFinished() {
                pd_check_version.dismiss();
                Log.e("---------","onFinished");
            }
        });
    }

}
