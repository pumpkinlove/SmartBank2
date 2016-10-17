package com.miaxis.smartbank.activity.function.doing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.adapter.BankDoingAdapter;
import com.miaxis.smartbank.domain.BankDoing;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.DateUtil;
import com.miaxis.smartbank.utils.XUtil;
import com.miaxis.smartbank.view.ImageDialog;
import com.miaxis.smartbank.view.XListView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_bank_doing)
public class BankDoingActivity extends BaseActivity implements XListView.IXListViewListener {

    private int page = 1;
    private boolean isClear;
    private int total = 0;

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

    @ViewInject(R.id.tv_right)
    private TextView tvRight;

    @ViewInject(R.id.xlv_bank_doing)
    private XListView xlvBankDoing;

    private List<BankDoing> bankDoingList;

    private BankDoingAdapter adapter;

    private ImageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {

        bankDoingList = new ArrayList<>();
        BankDoing doing = new BankDoing();
        dialog = new ImageDialog();

        doing.setOrganname("测试网点");
        doing.setContent("今天网点的主题是卡通人物啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了");
        doing.setOpdate(DateUtil.toMonthDay(new Date()));
        doing.setOptime(DateUtil.toHourMinString(new Date()));

        doing.setPhoto0("http://hi.csdn.net/attachment/201110/30/0_1319976939pkgr.gif");
        doing.setPhoto1("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3232292644,425916855&fm=111&gp=0.jpg");
        doing.setPhoto2("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3232292644,425916855&fm=111&gp=0.jpg");
        doing.setPhoto3("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");
        doing.setPhoto4("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3232292644,425916855&fm=111&gp=0.jpg");
        doing.setPhoto5("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");
        doing.setPhoto6("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3232292644,425916855&fm=111&gp=0.jpg");
        doing.setPhoto7("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");
        doing.setPhoto8("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3232292644,425916855&fm=111&gp=0.jpg");

        bankDoingList.add(doing);
        doing = new BankDoing();
        doing.setOrganname("测试网点");
        doing.setContent("今天网点的主题是卡通人物啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了");
        doing.setOpdate(DateUtil.toMonthDay(new Date()));
        doing.setOptime(DateUtil.toHourMinString(new Date()));

        doing.setPhoto0("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");

        bankDoingList.add(doing);
        doing = new BankDoing();
        doing.setOrganname("测试网点");
        doing.setContent("今天网点的主题是卡通人物啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了");
        doing.setOpdate(DateUtil.toMonthDay(new Date()));
        doing.setOptime(DateUtil.toHourMinString(new Date()));

        doing.setPhoto0("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");
        doing.setPhoto1("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3232292644,425916855&fm=111&gp=0.jpg");

        bankDoingList.add(doing);
        doing = new BankDoing();
        doing.setOrganname("测试网点");
        doing.setContent("今天网点的主题是卡通人物啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了");
        doing.setOpdate(DateUtil.toMonthDay(new Date()));
        doing.setOptime(DateUtil.toHourMinString(new Date()));

        doing.setPhoto0("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");
        doing.setPhoto1("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3232292644,425916855&fm=111&gp=0.jpg");
        doing.setPhoto2("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");

        bankDoingList.add(doing);
        doing = new BankDoing();
        doing.setOrganname("测试网点");
        doing.setContent("今天网点的主题是卡通人物啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了");
        doing.setOpdate(DateUtil.toMonthDay(new Date()));
        doing.setOptime(DateUtil.toHourMinString(new Date()));

        doing.setPhoto0("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3425590442,2523378451&fm=111&gp=0.jpg");

        bankDoingList.add(doing);

        adapter = new BankDoingAdapter(bankDoingList, this);
        adapter.setPhotoClickListener(new BankDoingAdapter.PhotoClickListener() {
            @Override
            public void onPhotoClick(String url) {
                dialog.setUrl(url);
                dialog.show(getFragmentManager(), "");
            }
        });

        xlvBankDoing.setAdapter(adapter);

    }

    @Override
    protected void initView() {

        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setText("网点活动");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(" + ");
        tvRight.setTextSize(25.0f);

        xlvBankDoing.setPullLoadEnable(true);
        xlvBankDoing.setPullRefreshEnable(true);
        xlvBankDoing.setXListViewListener(this);
        xlvBankDoing.setAdapter(adapter);
    }

    private void loadDoing() {
        String url = "";

        Map<String, Object> params = new HashMap<>();

        params.put("page", page);

        XUtil.Post(url, params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

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


    @Override
    public void onRefresh() {
        isClear = false;
        page ++;
    }

    @Override
    public void onLoadMore() {
        xlvBankDoing.stopRefresh();
        xlvBankDoing.stopLoadMore();
        xlvBankDoing.setRefreshTime("刚刚");
    }

    @Event(R.id.tv_left)
    private void goBack(View view) {
        finish();
    }

    @Event(R.id.tv_right)
    private void newDoingClick(View view) {
        startActivityForResult(new Intent(this, NewDoingActivity.class), 1);
    }

    private void refreshList() {

        String url = "http://192.168.5.96:8080/" + Constant.PROJECT_NAME + "/" ;



    }
}
