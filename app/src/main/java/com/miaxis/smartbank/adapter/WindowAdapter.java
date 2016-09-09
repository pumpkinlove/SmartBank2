package com.miaxis.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.WindowInfo;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/9/9.
 */
public class WindowAdapter extends RecyclerView.Adapter<WindowAdapter.WindowViewHolder> {

    private List<WindowInfo> windowInfoList;
    private Context context;
    private WindowListener listener;

    public WindowAdapter(List<WindowInfo> windowInfoList, Context context) {
        this.windowInfoList = windowInfoList;
        this.context = context;
    }

    @Override
    public WindowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WindowViewHolder(LayoutInflater.from(context).inflate(R.layout.item_window, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(WindowViewHolder holder, int position) {
        WindowInfo w = windowInfoList.get(position);
        if(w == null) {
            return;
        }
        holder.tvWindowNo.setText(w.getWindowNo());
        holder.tvWorkerName.setText(w.getWorkerName());
        holder.tvWindowType.setText(w.getBusiType());
        holder.tvAverageTime.setText(w.getAverageBusiTime());
        holder.tvStatus.setText(w.getStatus());
    }

    @Override
    public int getItemCount() {
        return windowInfoList.size();
    }

    class WindowViewHolder extends RecyclerView.ViewHolder {

        private WindowListener listener;

        @ViewInject(R.id.tv_window_no)
        private TextView tvWindowNo;
        @ViewInject(R.id.tv_window_worker_name)
        private TextView tvWorkerName;
        @ViewInject(R.id.tv_window_type)
        private TextView tvWindowType;
        @ViewInject(R.id.tv_window_average_time)
        private TextView tvAverageTime;
        @ViewInject(R.id.tv_window_status)
        private TextView tvStatus;

        public WindowViewHolder(View itemView, WindowListener listener) {
            super(itemView);
            x.view().inject(this, itemView);
            this.listener = listener;
        }

        @Event(R.id.ll_window)
        private void onItemClick(View view) {
            if(listener != null)
                listener.onItemClick(view, getPosition());
        }
    }

    public interface WindowListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(WindowListener listener) {
        this.listener = listener;
    }
}
