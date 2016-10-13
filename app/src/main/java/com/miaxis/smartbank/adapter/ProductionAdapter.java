package com.miaxis.smartbank.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.Production;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/10/10.
 */
public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.ViewHolder> {

    private RecyclerView recyclerView;

    private List<Production> productionList;

    public ProductionAdapter(List<Production> productionList,RecyclerView recyclerView) {
        this.productionList = productionList;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_production, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return productionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.el_describe)
        private ExpandableLayout elDescribe;
        @ViewInject(R.id.el_term)
        private ExpandableLayout elTerm;

        @ViewInject(R.id.tv_eb_describe)
        private TextView tvEbDescribe;
        @ViewInject(R.id.tv_eb_term)
        private TextView tvEbTerm;
        @ViewInject(R.id.tv_describe)
        private TextView tvDescribe;
        @ViewInject(R.id.tv_term)
        private TextView tvTerm;
        @ViewInject(R.id.iv_production_photo)
        private ImageView ivProduction;

        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);

        }

        public void bind(int position) {
            this.position = position;
            ImageOptions options = new ImageOptions.Builder()
                    // 是否忽略GIF格式的图片
                    .setIgnoreGif(false)
                    // 图片缩放模式
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    // 下载中显示的图片
                    .setLoadingDrawableId(R.mipmap.product_default)
                    // 下载失败显示的图片
                    .setFailureDrawableId(R.mipmap.product_default)
                    // 得到ImageOptions对象
                    .build();
            x.image().bind(ivProduction, productionList.get(position).getPicUrl(), options);
            tvDescribe.setText(productionList.get(position).getDescribe());
            tvTerm.setText(productionList.get(position).getTerm());

            tvEbDescribe.setSelected(false);
            elDescribe.collapse(false);

            tvEbTerm.setSelected(false);
            elTerm.collapse(false);

        }

        @Event(R.id.tv_eb_describe)
        private void expandDescribe(View view) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(-1);
            if (holder != null) {
                holder.tvEbDescribe.setSelected(false);
                holder.elDescribe.collapse();
            }
            if (!elDescribe.isExpanded()) {
               elDescribe.expand();
            } else {
                elDescribe.collapse();
            }

        }

        @Event(R.id.tv_eb_term)
        private void expandTerm(View view) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(-1);
            if (holder != null) {
                holder.tvEbTerm.setSelected(false);
                holder.elTerm.collapse();
            }

            if (!elTerm.isExpanded()) {
                elTerm.expand();
            } else {
                elTerm.collapse();
            }

        }

    }
}