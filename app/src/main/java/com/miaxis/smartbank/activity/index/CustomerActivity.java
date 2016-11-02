package com.miaxis.smartbank.activity.index;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.Customer;
import com.miaxis.smartbank.utils.DateUtil;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.net.URLDecoder;

import Decoder.BASE64Decoder;

@ContentView(R.layout.activity_customer)
public class CustomerActivity extends BaseActivity {

    private Customer customer;

    @ViewInject(R.id.iv_c_photo)
    private ImageView iv_c_photo;

    @ViewInject(R.id.tv_c_name)
    private TextView tv_c_name;

    @ViewInject(R.id.tv_c_age)
    private TextView tv_c_age;

    @ViewInject(R.id.tv_c_gender)
    private TextView tv_c_gender;

    @ViewInject(R.id.tv_c_id)
    private TextView tv_c_id;

    @ViewInject(R.id.tv_c_nation)
    private TextView tv_c_nation;

    @ViewInject(R.id.tv_c_race)
    private TextView tv_c_race;

    @ViewInject(R.id.tv_c_birthday)
    private TextView tv_c_birthday;

    @ViewInject(R.id.tv_c_job)
    private TextView tv_c_job;

    @ViewInject(R.id.tv_c_address)
    private TextView tv_c_address;

    @ViewInject(R.id.tv_c_contact)
    private TextView tv_c_contact;

    @ViewInject(R.id.tv_middle)
    private TextView tv_middle;

    @ViewInject(R.id.tv_left)
    private TextView tv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        customer = (Customer) getIntent().getSerializableExtra("customer");
    }

    @Override
    protected void initView() {
        tv_middle.setText("客户信息");
        tv_left.setVisibility(View.VISIBLE);
    }

    private void downLoadCustomer(String customerName){
        RequestParams params = new RequestParams("http://"+ MyApplication.config.getIp()+":" + MyApplication.config.getPort() + "/CIIPS_A/customer/select.action");

        params.setCharset("utf-8");
        params.addParameter("customname",customerName);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    String reJson = response;
                    reJson = URLDecoder.decode(reJson,"utf-8");
                    Gson g = new Gson();
                    customer =  g.fromJson(reJson,Customer.class);
                    fetchCustomer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(Callback.CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void fetchCustomer(){
        String b = customer.getBirthday();
        tv_c_name.setText(customer.getCustomname());
        tv_c_age.setText(DateUtil.getAge(customer.getBirthday())+" 周岁");
        tv_c_gender.setText(customer.getSex());
        tv_c_birthday.setText(customer.getBirthday());
        tv_c_id.setText(customer.getCardid().substring(0,14)+"****");
        tv_c_address.setText(customer.getAddress());
        tv_c_job.setText(customer.getCareer());
        tv_c_nation.setText(customer.getNation());
        tv_c_race.setText(customer.getMz());
        tv_c_contact.setText(customer.getTelphone());
        byte[] bitmapArray = new byte[0];
        try {
            bitmapArray = (new BASE64Decoder()).decodeBuffer(customer.getCardpic());
            iv_c_photo.setImageBitmap(BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        finish();
    }
}
