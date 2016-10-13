package com.miaxis.smartbank.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.miaxis.smartbank.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/10/12.
 */
public class ImageDialog extends DialogFragment {

    @ViewInject(R.id.iv_dialog)
    private ImageView imageView;

    private ImageView preImageView;

    private String url;

    public void setPreImageView(ImageView preImageView) {
        this.preImageView = preImageView;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onStart() {
        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            DisplayMetrics dm = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_image, container);
        x.view().inject(this, view);
        ImageOptions options = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
                // 图片缩放模式
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                // 下载中显示的图片
//                        .setLoadingDrawableId(R.mipmap.product_default)
                // 下载失败显示的图片
//                        .setFailureDrawableId(R.mipmap.product_default)
                // 得到ImageOptions对象
                .build();
        x.image().bind(imageView, url, options);

        return view;
    }


}
