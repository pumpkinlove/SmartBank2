package com.miaxis.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.Notice;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/9/8.
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private List<Notice> noticeList;
    private Context context;
    private NoticeListener listener;

    public NoticeAdapter(List<Notice> noticeList, Context context) {
        this.noticeList = noticeList;
        this.context = context;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        Notice n = noticeList.get(position);
        if(n == null){
            return;
        }
        holder.tvNoticeTime.setText(n.getOptime());
        holder.tvNoticeTitle.setText(n.getTitle());
        if(n.isRead()){
            holder.llNotice.setBackground(context.getDrawable(R.drawable.dark3_bg_white_ripple));
        }else{
            holder.llNotice.setBackground(context.getDrawable(R.drawable.red_light_bg_white_ripple));
        }

    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.tvNoticeTitle)
        private TextView tvNoticeTitle;

        @ViewInject(R.id.tvNoticeTime)
        private TextView tvNoticeTime;

        @ViewInject(R.id.ll_item_notice)
        private LinearLayout llNotice;

        private NoticeListener listener;

        public NoticeViewHolder(View itemView, NoticeListener listener) {
            super(itemView);
            this.listener = listener;
            x.view().inject(this, itemView);
        }

        @Event(R.id.ll_item_notice)
        private void onClick(View view) {
            if(listener != null) {
                listener.onItemClick(view, getPosition());
            }
        }
    }

    public interface NoticeListener{
        void onItemClick(View view, int position);
    }

    public void setNoticeListener(NoticeListener listener){
        this.listener = listener;
    }
}
