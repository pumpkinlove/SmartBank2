package com.miaxis.smartbank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.BankDoing;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.view.ImageDialog;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/10/9.
 */
public class BankDoingAdapter extends BaseAdapter {

    private List<BankDoing> bankDoingList;

    private Context context;

    private ImageDialog dialog;

    private PhotoClickListener listener;

    public BankDoingAdapter(List<BankDoing> bankDoingList, Context context) {
        this.bankDoingList = bankDoingList;
        this.context = context;
        dialog = new ImageDialog();
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

            final BankDoing doing = bankDoingList.get(i);
            if (doing != null) {

                holder.tvBankName.setText(doing.getOrganname());
                holder.tvContent.setText(doing.getContent());
                ImageOptions options = new ImageOptions.Builder()
                        .setFadeIn(true)
                        // 是否忽略GIF格式的图片
                        .setIgnoreGif(false)
                        // 图片缩放模式
                        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        // 下载中显示的图片
//                        .setLoadingDrawableId(R.mipmap.product_default)
                        // 下载失败显示的图片
//                        .setFailureDrawableId(R.mipmap.product_default)
                        // 得到ImageOptions对象
                        .build();

                if (doing.getPhoto0() != null && doing.getPhoto0().length() > 0) {
                    x.image().bind(holder.ivDoing0, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +   doing.getPhoto0(), options);
                    holder.ivDoing0.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto1() != null && doing.getPhoto1().length() > 0) {
                    x.image().bind(holder.ivDoing1, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto1(), options);
                    holder.ivDoing1.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto2() != null && doing.getPhoto2().length() > 0) {
                    x.image().bind(holder.ivDoing2, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto2(), options);
                    holder.ivDoing2.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto3() != null && doing.getPhoto3().length() > 0) {
                    x.image().bind(holder.ivDoing3, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto3(), options);
                    holder.ivDoing3.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto4() != null && doing.getPhoto4().length() > 0) {
                    x.image().bind(holder.ivDoing4, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto4(), options);
                    holder.ivDoing4.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto5() != null && doing.getPhoto5().length() > 0) {
                    x.image().bind(holder.ivDoing5, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto5(), options);
                    holder.ivDoing5.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto6() != null && doing.getPhoto6().length() > 0) {
                    x.image().bind(holder.ivDoing6, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto6(), options);
                    holder.ivDoing6.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto7() != null && doing.getPhoto7().length() > 0) {
                    x.image().bind(holder.ivDoing7, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto7(), options);
                    holder.ivDoing7.setVisibility(View.VISIBLE);
                }

                if (doing.getPhoto8() != null && doing.getPhoto8().length() > 0) {
                    x.image().bind(holder.ivDoing8, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  doing.getPhoto8(), options);
                    holder.ivDoing8.setVisibility(View.VISIBLE);
                }

                holder.ivDoing0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto0());
                    }
                });

                holder.ivDoing1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto1());
                    }
                });

                holder.ivDoing2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto2());
                    }
                });

                holder.ivDoing3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto3());
                    }
                });

                holder.ivDoing4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto4());
                    }
                });

                holder.ivDoing5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto5());
                    }
                });

                holder.ivDoing6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto6());
                    }
                });

                holder.ivDoing7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto7());
                    }
                });

                holder.ivDoing8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPhotoClick(doing.getPhoto8());
                    }
                });

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

    public interface PhotoClickListener {
        void onPhotoClick(String url);
    }

    public void setPhotoClickListener(PhotoClickListener listener) {
        this.listener = listener;
    }

    private void adjustPhoto(ImageView imageView) {

    }

}
