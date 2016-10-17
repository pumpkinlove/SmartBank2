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
import android.widget.TextView;

import com.miaxis.smartbank.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/10/12.
 */
public class ImageDialog extends DialogFragment {

    @ViewInject(R.id.iv_dialog)
    private ImageView imageView;

    @ViewInject(R.id.tv_id_bottom)
    private TextView tv_id_bottom;

    private View.OnClickListener listener;

    private ImageView preImageView;

    private String url;

    private boolean buttonFlag = false;

    public void setPreImageView(ImageView preImageView) {
        this.preImageView = preImageView;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setButtonFlag(boolean buttonFlag) {
        this.buttonFlag = buttonFlag;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_image, container);
        x.view().inject(this, view);

        if (buttonFlag) {
            tv_id_bottom.setVisibility(View.VISIBLE);
        } else {
            tv_id_bottom.setVisibility(View.GONE);
        }

        ImageOptions options = new ImageOptions.Builder()
                .setIgnoreGif(false)
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                .build();
        x.image().bind(imageView, url, options);

        return view;
    }

    @Event(R.id.tv_id_bottom)
    private void tvClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

}
