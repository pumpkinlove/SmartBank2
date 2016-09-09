package com.miaxis.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.QueueInfo;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/9/9.
 */
public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueViewHolder> {

    private List<QueueInfo> queueInfoList;
    private Context context;
    private QueueListener listener;

    public QueueAdapter(List<QueueInfo> queueInfoList, Context context) {
        this.queueInfoList = queueInfoList;
        this.context = context;
    }

    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QueueViewHolder(LayoutInflater.from(context).inflate(R.layout.item_queue_info, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(QueueViewHolder holder, int position) {
        QueueInfo q = queueInfoList.get(position);
        if(q == null) {
            return;
        }
        holder.tvQueueTypeName.setText(q.getTypeName());
        holder.tvQueueWaitNum.setText(q.getWaitNum());
        holder.tvQueueCurNum.setText(q.getCurNum());
        holder.tvQueueNextNum.setText(q.getNextNum());
        holder.tvAverageWaitTime.setText(q.getAverageWaitTime());
    }

    @Override
    public int getItemCount() {
        return queueInfoList.size();
    }

    class QueueViewHolder extends RecyclerView.ViewHolder {

        private QueueListener listener;
        @ViewInject(R.id.tv_queue_type_name)
        private TextView tvQueueTypeName;
        @ViewInject(R.id.tv_queue_wait_num)
        private TextView tvQueueWaitNum;
        @ViewInject(R.id.tv_queue_cur_num)
        private TextView tvQueueCurNum;
        @ViewInject(R.id.tv_queue_next_num)
        private TextView tvQueueNextNum;
        @ViewInject(R.id.tv_queue_average_wait_time)
        private TextView tvAverageWaitTime;

        public QueueViewHolder(View itemView, QueueListener listener) {
            super(itemView);
            x.view().inject(this, itemView);
            this.listener = listener;
        }

        @Event(R.id.ll_queue)
        private void onItemClick(View view) {
            if(listener != null)
                listener.onItemClick(view, getPosition());
        }
    }

    public interface QueueListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(QueueListener listener) {
        this.listener = listener;
    }
}
