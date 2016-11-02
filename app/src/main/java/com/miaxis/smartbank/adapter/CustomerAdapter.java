package com.miaxis.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.Customer;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/11/1.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private Context context;
    private CustomerListener listener;

    public CustomerAdapter() {
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false), listener);
    }

    public CustomerAdapter(List<Customer> customerList, Context context) {
        this.customerList = customerList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        holder.tv_name.setText(customerList.get(position).getCustomname());
        holder.tv_message.setText(customerList.get(position).getCustomname());
        holder.tv_time.setText(customerList.get(position).getComeTime());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.tv_name)
        private TextView tv_name;
        @ViewInject(R.id.tv_message)
        private TextView tv_message;
        @ViewInject(R.id.tv_time)
        private TextView tv_time;

        private CustomerListener listener;

        public CustomerViewHolder(View itemView, CustomerListener listener) {
            super(itemView);
            this.listener = listener;
            x.view().inject(this, itemView);
        }

        @Event(R.id.ll_item_customer)
        private void onClick(View view) {
            listener.onClick(view, getPosition());
        }
    }

    public interface CustomerListener {
        void onClick(View view, int position);
    }

    public void setListener(CustomerListener listener) {
        this.listener = listener;
    }
}
