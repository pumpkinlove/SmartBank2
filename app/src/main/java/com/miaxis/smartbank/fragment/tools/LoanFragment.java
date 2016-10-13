package com.miaxis.smartbank.fragment.tools;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.adapter.LoanItemAdapter;
import com.miaxis.smartbank.domain.LoanItem;
import com.miaxis.smartbank.utils.CommonUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanFragment extends Fragment {

    @ViewInject(R.id.et_loan_money)
    private EditText et_loan_money;

    @ViewInject(R.id.et_loan_month)
    private EditText et_loan_month;

    @ViewInject(R.id.et_loan_rate)
    private EditText et_loan_rate;

    @ViewInject(R.id.lv_loan_result)
    private ListView lv_loan_result;

    public LoanFragment() {
        // Required empty public constructor
    }

    private List<LoanItem> loanItemList;
    private LoanItemAdapter loanItemAdapter;

    private double loan_money = 0.0;
    private int loan_month = 0;
    private double loan_rate = 0.0;
    private int loanType;

    @ViewInject(R.id.sp_loanType)
    private Spinner sp_type;
    private ArrayAdapter<String> spAdapter;
    private static final String[] loanTypes = {"等额本息还款","等额本金还款"};

    @ViewInject(R.id.ll_loan_result)
    private LinearLayout ll_loan_result;

    @ViewInject(R.id.loan_total)
    private TextView loan_total;
    private double total_loan;
    @ViewInject(R.id.loan_interest_total)
    private TextView loan_interest_total;
    private double total_interest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loan, container, false);
        x.view().inject(this, v);

        initData();
        initView();

        return v;
    }

    private void initData() {
        spAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, loanTypes);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loanItemList = new ArrayList<>();
        loanItemAdapter = new LoanItemAdapter(getContext(), loanItemList);
    }

    private void initView() {
        sp_type.setAdapter(spAdapter);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loanType = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lv_loan_result.setAdapter(loanItemAdapter);
        loanItemAdapter.notifyDataSetChanged();
    }

    @Event(R.id.tv_calculate)
    private void calculateFinance(View view){

        if (et_loan_money.length() == 0 || et_loan_month.length() == 0 || et_loan_rate.length() == 0) {
           return;
        }

        total_interest = 0;
        total_loan = 0;
        loan_total.setText("");
        loan_interest_total.setText("");
        ll_loan_result.setVisibility(View.VISIBLE);
        loanItemList.clear();
        loanItemAdapter.notifyDataSetChanged();
        if(et_loan_money.length() < 1){
            return;
        }
        if(et_loan_month.length() < 1){
            return;
        }
        if(et_loan_rate.length() < 1){
            return;
        }

        loan_money = Double.valueOf(et_loan_money.getText().toString());
        loan_month = Integer.valueOf(et_loan_month.getText().toString());
        loan_rate = Double.valueOf(et_loan_rate.getText().toString()) / 100;
        if(loanType == 0){      //等额本息
            double pb = loan_money;
            for(int i=1;i<=loan_month;i++){
                LoanItem item = new LoanItem();
                item.setPeriod( i + "" );
                double total = loan_money * ( loan_rate * Math.pow ( ( 1 + loan_rate ) , loan_month ) ) / ( Math.pow( ( 1 + loan_rate), loan_month ) - 1 );
                item.setTotal(CommonUtil.xround(total , 2) );
                double interest = loan_money * loan_rate * Math.pow( 1+loan_rate, loan_month  ) / (Math.pow( 1+loan_rate, loan_month ) - 1) - loan_money * loan_rate * Math.pow(1+loan_rate, (i-1)) / (Math.pow( 1+loan_rate, loan_month ) - 1);
                item.setInterest(CommonUtil.xround(interest , 2));
                double principal = total - interest;
                item.setPrincipal(CommonUtil.xround(principal , 2));
                pb -= principal;
                item.setPrincipalBalance(CommonUtil.xround(pb, 2));

                Log.e("-------------", item.getPeriod()+"----"+item.getTotal());
                loanItemList.add(item);
                total_loan += item.getTotal();
                total_interest += item.getInterest();
            }
        }else if(loanType == 1){    //等额本金
            for(int i=1;i<=loan_month;i++){
                LoanItem item = new LoanItem();
                item.setPeriod( i + "" );
                item.setTotal(CommonUtil.xround(( loan_money/loan_month +  loan_rate * ( loan_money - loan_money/loan_month * (i-1) ) ), 2));
                item.setPrincipal(CommonUtil.xround( loan_money/loan_month ,2));
                item.setInterest(CommonUtil.xround( ( loan_rate * ( loan_money - loan_money/loan_month * (i-1) )) ,2));
                item.setPrincipalBalance(CommonUtil.xround((loan_money -  loan_money/loan_month * i), 2));
                Log.e("-------------", item.getPeriod()+"----"+item.getTotal());
                loanItemList.add(item);
                total_loan += item.getTotal();
                total_interest += item.getInterest();
            }
        }

        loanItemAdapter.notifyDataSetChanged();
        loan_total.setText(CommonUtil.xround(total_loan, 2) +"");
        loan_interest_total.setText(CommonUtil.xround(total_interest, 2) +"");
    }

    @Event(R.id.tv_clear)
    private void clear(View view){
        ll_loan_result.setVisibility(View.INVISIBLE);
        loan_money = 0.0;
        loan_month = 0;
        loan_rate = 0.0;
        et_loan_money.setText("");
        et_loan_month.setText("");
        et_loan_rate.setText("");
        loan_total.setText("");
        loan_interest_total.setText("");
        loanItemList.clear();
    }
}
