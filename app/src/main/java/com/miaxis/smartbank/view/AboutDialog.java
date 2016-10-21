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
import android.widget.TextView;

import com.miaxis.smartbank.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/10/21.
 */

public class AboutDialog extends DialogFragment {

    @ViewInject(R.id.ad_tv_about)
    private TextView ad_tv_about;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.77), (int) (dm.heightPixels * 0.33));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_about, container);
        x.view().inject(this, view);

        return view;
    }

}
