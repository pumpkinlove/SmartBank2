package com.miaxis.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.Production;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by xu.nan on 2016/10/10.
 */
public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.ViewHolder> {
    private static final int UNSELECTED = -1;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;

    private List<Production> productionList;

    private Context context;

    public ProductionAdapter(List<Production> productionList, Context context, RecyclerView recyclerView) {
        this.productionList = productionList;
        this.context = context;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @ViewInject(R.id.expandable_layout)
        private ExpandableLayout expandableLayout;

        private TextView expandButton;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
            expandButton = (TextView) itemView.findViewById(R.id.expand_button);

            expandButton.setOnClickListener(this);
        }

        public void bind(int position) {
            this.position = position;

            expandButton.setText(productionList.get(position).getName());
            expandButton.setSelected(false);
            expandableLayout.collapse(false);

        }

        @Override
        public void onClick(View view) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.expandButton.setSelected(false);
                holder.expandableLayout.collapse();
            }

            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                expandButton.setSelected(true);
                expandableLayout.expand();
                selectedItem = position;
            }
        }
    }
}