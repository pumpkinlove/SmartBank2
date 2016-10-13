package com.miaxis.smartbank.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.Version;

import org.xutils.x;

/**
 * Created by xu.nan on 2016/9/12.
 */
public class UpdateDialog extends DialogFragment {

    private Version curVersion;
    private Version lastVersion;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.77), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_update, container);
        x.view().inject(this, view);

        return view;
    }

    public void setCurVersion(Version curVersion) {
        this.curVersion = curVersion;
    }

    public void setLastVersion(Version lastVersion) {
        this.lastVersion = lastVersion;
    }

    private void cancelClick(View view) {
        dismiss();
    }
}
