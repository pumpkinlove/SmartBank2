package com.miaxis.smartbank.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.utils.CommonUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_config)
public class ConfigActivity extends BaseActivity {

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;
    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

    @ViewInject(R.id.et_ip)
    private EditText et_ip;
    @ViewInject(R.id.et_port)
    private EditText et_port;

    @ViewInject(R.id.et_emqtt_ip)
    private EditText et_emqtt_ip;

    @ViewInject(R.id.et_emqtt_port)
    private EditText et_emqtt_port;

    @ViewInject(R.id.et_clientid)
    private EditText et_clientid;


    private Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        config = ((MyApplication) getApplicationContext()).config;
    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setText("设置");

        if (config != null) {
            et_ip.setText(config.getIp());
            et_port.setText(config.getPort());
            et_emqtt_ip.setText(config.getEmqttIp());
            et_emqtt_port.setText(config.getEmqttPort());
            et_clientid.setText(config.getClientId());
        } else {
            config = new Config();
            config.setId(1);
        }

    }

    @Event(R.id.tv_left)
    private void back(View view) {
        finish();
    }

    @Event(R.id.tv_save)
    private void saveConfig(View view) {
        config.setId(1);
        config.setIp(et_ip.getText().toString());
        config.setPort(et_port.getText().toString());
        config.setEmqttIp(et_emqtt_ip.getText().toString());
        config.setEmqttPort(et_emqtt_port.getText().toString());
        config.setClientId(et_clientid.getText().toString());
        DbManager dbManager = x.getDb(((MyApplication) getApplicationContext()).daoConfig);
        try {
            dbManager.saveOrUpdate(config);
        } catch (DbException e) {
            e.printStackTrace();
        }
        ((MyApplication) getApplicationContext()).initConfig();
        finish();
    }
}
