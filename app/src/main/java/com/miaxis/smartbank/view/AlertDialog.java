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
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/10/14.
 */

public class AlertDialog extends DialogFragment {

    @ViewInject(R.id.tv_ad_content)
    private TextView tv_ad_content;

    private String adContent;

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.77), (int) (dm.heightPixels * 0.17));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_alert, container);
        x.view().inject(this, view);

        tv_ad_content.setText(adContent);

        return view;
    }

}
