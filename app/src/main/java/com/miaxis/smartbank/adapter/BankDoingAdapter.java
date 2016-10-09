package com.miaxis.smartbank.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.BankDoing;

import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/10/9.
 */
public class BankDoingAdapter extends BaseAdapter {

    private List<BankDoing> bankDoingList;

    private Context context;

    public BankDoingAdapter(List<BankDoing> bankDoingList, Context context) {
        this.bankDoingList = bankDoingList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bankDoingList.size();
    }

    @Override
    public Object getItem(int i) {
        return bankDoingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = null;
        try {
            ViewHolder holder = new ViewHolder();
            if (view == null) {
                v = View.inflate(context, R.layout.item_bank_doing, null);

                holder.ivHead = (ImageView) v.findViewById(R.id.iv_doing_head);

                holder.tvBankName = (TextView) v.findViewById(R.id.tv_doing_bank_name);
                holder.tvContent = (TextView) v.findViewById(R.id.tv_doing_content);

                holder.ivDoing0 = (ImageView) v.findViewById(R.id.iv_doing0);
                holder.ivDoing1 = (ImageView) v.findViewById(R.id.iv_doing1);
                holder.ivDoing2 = (ImageView) v.findViewById(R.id.iv_doing2);
                holder.ivDoing3 = (ImageView) v.findViewById(R.id.iv_doing3);
                holder.ivDoing4 = (ImageView) v.findViewById(R.id.iv_doing4);
                holder.ivDoing5 = (ImageView) v.findViewById(R.id.iv_doing5);
                holder.ivDoing6 = (ImageView) v.findViewById(R.id.iv_doing6);
                holder.ivDoing7 = (ImageView) v.findViewById(R.id.iv_doing7);
                holder.ivDoing8 = (ImageView) v.findViewById(R.id.iv_doing8);

                holder.tvOptime = (TextView) v.findViewById(R.id.tv_doing_optime);

                v.setTag(holder);
            } else {
                v = view;
                holder = (ViewHolder) view.getTag();
            }

            BankDoing doing = bankDoingList.get(i);
            if (doing != null) {

                holder.tvBankName.setText(doing.getBankName());
                holder.tvContent.setText(doing.getContent());

                x.image().bind(holder.ivDoing0, doing.getPhoto0());
                x.image().bind(holder.ivDoing1, doing.getPhoto1());
                x.image().bind(holder.ivDoing2, doing.getPhoto2());
                x.image().bind(holder.ivDoing3, doing.getPhoto3());
                x.image().bind(holder.ivDoing4, doing.getPhoto4());
                x.image().bind(holder.ivDoing5, doing.getPhoto5());
                x.image().bind(holder.ivDoing6, doing.getPhoto6());
                x.image().bind(holder.ivDoing7, doing.getPhoto7());
                x.image().bind(holder.ivDoing8, doing.getPhoto8());

                holder.tvOptime.setText(doing.getOpdate() + " " + doing.getOptime());

            }

        } catch (Exception e) {

        }

        return v;
    }

    static class ViewHolder {

        ImageView ivHead;
        TextView tvBankName;
        TextView tvContent;
        ImageView ivDoing0;
        ImageView ivDoing1;
        ImageView ivDoing2;
        ImageView ivDoing3;
        ImageView ivDoing4;
        ImageView ivDoing5;
        ImageView ivDoing6;
        ImageView ivDoing7;
        ImageView ivDoing8;
        TextView tvOptime;

    }

    public void setBankDoingList(List<BankDoing> list) {
        this.bankDoingList = list;
    }

}
