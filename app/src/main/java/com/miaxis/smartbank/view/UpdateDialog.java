package com.miaxis.smartbank.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.domain.Version;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.DbUtil;
import com.miaxis.smartbank.utils.XUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * Created by xu.nan on 2016/9/12.
 */
public class UpdateDialog extends DialogFragment {

    private Version curVersion;
    private Version lastVersion;

    @ViewInject(R.id.ud_content)
    private TextView ud_content;

    @ViewInject(R.id.pb_update)
    private ContentLoadingProgressBar pb_update;

    @ViewInject(R.id.ll_ud_bottom)
    private LinearLayout ll_ud_bottom;

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

        initDialog();

        return view;
    }

    private void initDialog() {
        ud_content.setText("发现新版本 " + lastVersion.getVersionName() + "， 是否立刻升级？");
        pb_update.setVisibility(View.GONE);
    }

    public void setCurVersion(Version curVersion) {
        this.curVersion = curVersion;
    }

    public void setLastVersion(Version lastVersion) {
        this.lastVersion = lastVersion;
    }

    @Event(R.id.ud_cancel)
    private void cancelClick(View view) {
        dismiss();
    }

    @Event(R.id.ud_confirm)
    private void downLoadNewVersion(View view){
        pb_update.setVisibility(View.VISIBLE);
        final String filepath = Environment.getExternalStorageDirectory().getPath()+"/智慧银行_" + lastVersion.getVersionName()+".apk";
        Log.e("--------------",filepath);
//        DbManager.DaoConfig daoConfig  = DbUtil.getDaoConfig();
//        DbManager dbManager = x.getDb(daoConfig);
//        Config config = null;
//        try {
//            config = dbManager.findFirst(Config.class);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        String url = "http://" + config.getIp() + ":" + config.getPort() + "/CIIPS_A/version/downLoadLastVersion.action";
//        String urlDemo = "http://192.168.5.162:8080/CIIPS_A/version/downLoadLastVersion.action";
        String urlDemo = "http://192.168.5.96:8080/"+ Constant.PROJECT_NAME + "/" + Constant.DOWN_VERSION + "?organid=1001&versiontype=03";
        XUtil.DownLoadFile(urlDemo, filepath, new Callback.ProgressCallback<File>(){
            @Override
            public void onStarted() {
                ud_content.setText("开始下载...");
            }

            @Override
            public void onSuccess(File result) {
                ud_content.setText("下载完毕！");
                pb_update.setVisibility(View.GONE);
                Log.e("--------","onSuccess");
                File file = new File(filepath);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                startActivity(intent);
                dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ud_content.setText("升级失败！");
                Log.e("--------","onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                ud_content.setText("升级取消！");
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {
                ud_content.setText("暂停...");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                ud_content.setText("正在下载...");
                Log.e("--------","onLoading");
                if(isDownloading){
                    Log.e("--------",current+"///"+total);
                }
                pb_update.setMax((int)total);
                pb_update.setProgress((int)current);
                ll_ud_bottom.setVisibility(View.INVISIBLE);
            }

        });
    }
}
