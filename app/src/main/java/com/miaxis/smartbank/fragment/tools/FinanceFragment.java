package com.miaxis.smartbank.fragment.tools;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.miaxis.smartbank.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinanceFragment extends Fragment {

    @ViewInject(R.id.et_money)
    private EditText et_finance_money;

    @ViewInject(R.id.et_year)
    private EditText et_finance_year;

    @ViewInject(R.id.et_rate)
    private EditText et_finance_rate;

    @ViewInject(R.id.tv_total)
    private TextView tv_total;

    @ViewInject(R.id.tv_earn)
    private TextView tv_earn;

    private double f_money = 0.0;
    private double f_year = 0.0;
    private double f_rate = 0.0;
    private double f_result_earn = 0.0;
    private double f_result_total = 0.0;

    public FinanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_finance, container, false);

        x.view().inject(this, v);

        return v;
    }

    @Event(R.id.tv_calculate)
    private void calculateFinance(View view){
        if(et_finance_money.length() < 1){
            return;
        }
        if(et_finance_year.length() < 1){
            return;
        }
        if(et_finance_rate.length() < 1){
            return;
        }
        f_money = Double.valueOf(et_finance_money.getText().toString());
        f_year = Double.valueOf(et_finance_year.getText().toString());
        f_rate = Double.valueOf(et_finance_rate.getText().toString());
        f_result_total = Math.pow(( 1 + f_rate/100), f_year) * f_money ;
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        f_result_earn = f_result_total - f_money;
        tv_earn.setText(dcmFmt.format(f_result_earn));
        tv_total.setText(dcmFmt.format(f_result_total));

    }

    @Event(R.id.tv_clear)
    private void clearFinance(View view){
        f_money = 0.0;
        f_year = 0.0;
        f_rate = 0.0;
        f_result_earn = 0.0;
        f_result_total = 0.0;
        et_finance_money.setText("");
        et_finance_year.setText("");
        et_finance_rate.setText("");
        tv_earn.setText("");
        tv_total.setText("");
    }

}
