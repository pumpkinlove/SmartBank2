package com.miaxis.smartbank.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.utils.CommonUtil;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.XUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

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

    @ViewInject(R.id.sp_org)
    private Spinner sp_org;

    private List<String> orgList;
    private ArrayAdapter<String> orgAdapter;

    private Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();
        selectOrg();
    }

    @Override
    protected void initData() {
        config = ((MyApplication) getApplicationContext()).config;
        orgList = new ArrayList<>();
        orgAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, orgList);
        orgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    protected void initView() {
        sp_org.setAdapter(orgAdapter);
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
        config.setOrganname(sp_org.getSelectedItem().toString().split(" ")[0]);
        config.setOrganid(sp_org.getSelectedItem().toString().split(" ")[1]);
        DbManager dbManager = x.getDb(((MyApplication) getApplicationContext()).daoConfig);
        try {
            dbManager.saveOrUpdate(config);
        } catch (DbException e) {
            e.printStackTrace();
        }
        ((MyApplication) getApplicationContext()).initConfig();
        finish();
    }

    private void selectOrg() {
        String url = MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" + Constant.ORG_LIST;
        XUtil.Get(url, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("result", result);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);
                JsonArray a = element.getAsJsonArray();
                orgList.clear();
                for (int i = 0; i < a.size(); i++) {
                    JsonObject o = a.get(i).getAsJsonObject();
                    orgList.add(o.get("name").getAsString() + " " +o.get("organid").getAsString());
                }
                orgAdapter.notifyDataSetChanged();

                int k = orgAdapter.getCount();
                for(int i = 0; i < k; i++){
                    if ((config.getOrganname()+" "+config.getOrganid()).equals(orgAdapter.getItem(i).toString())) {
                        sp_org.setSelection(i,true);
                        break;
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
