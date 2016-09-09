package com.miaxis.smartbank.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.domain.Version;
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
 * Created by xu.nan on 2016/8/25.
 */
public class JoshuaUpdateDialog extends DialogFragment {

    @ViewInject(R.id.tv_dialog_confirm)
    private TextView tv_confirm;
    @ViewInject(R.id.tv_dialog_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_version_content)
    private TextView tv_version_content;
    @ViewInject(R.id.tv_update_content)
    private TextView tv_update_content;
    @ViewInject(R.id.sv_version_content)
    private ScrollView sv_version_content;

    @ViewInject(R.id.pb_update)
    private ContentLoadingProgressBar pb_update;

    private View.OnClickListener confirmListener;
    private View.OnClickListener cancelListener;

    private Version lastVersion;

    @Override
    public void onStart() {
        Log.e("------------","onStart");
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
        Log.e("------------","onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("------------","onCreateView");
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.layout_joshua_update_dialog, container);
        x.view().inject(this, view);

        initData();
        initView();

        return view;
    }

    private void initData(){

    }

    private void initView(){
        if(lastVersion == null){
            return;
        }
        tv_update_content.setText(lastVersion.getDescript());
        String htmlStr =
                "<html><body> 发现新版本 <span><font color=\"#FF4081\"> V" + lastVersion.getVersionName() + "</span> 是否立刻升级？</body></html>";
        tv_version_content.setText(Html.fromHtml(htmlStr));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.e("------------","onDismiss");
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroy() {
        Log.e("------------","onDestroy");
        super.onDestroy();
    }

    @Event(R.id.tv_dialog_confirm)
    private void downLoadNewVersion(View view){
        pb_update.setVisibility(View.VISIBLE);
        tv_version_content.setText("正在下载...");
        sv_version_content.setVisibility(View.GONE);
        final String filepath = Environment.getExternalStorageDirectory().getPath()+"/IA_" + lastVersion.getVersionName()+".apk";
        Log.e("--------------",filepath);
        DbManager.DaoConfig daoConfig  = DbUtil.getDaoConfig();
        DbManager dbManager = x.getDb(daoConfig);
        Config config = null;
        try {
            config = dbManager.findFirst(Config.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        String url = "http://" + config.getIp() + ":" + config.getPort() + "/CIIPS_A/version/downLoadLastVersion.action";
        String urlDemo = "http://192.168.5.123:8080/CIIPS_A/version/downLoadLastVersion.action";
        XUtil.DownLoadFile(urlDemo, filepath, new Callback.ProgressCallback<File>(){
            @Override
            public void onStarted() {

            }

            @Override
            public void onSuccess(File result) {
                pb_update.setVisibility(View.GONE);
                Log.e("--------","onSuccess");
                File file = new File(filepath);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                startActivity(intent);
                tv_version_content.setText("下载完毕！");
                tv_confirm.setText("重新下载");
                tv_confirm.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("--------","onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.e("--------","onLoading");
                if(isDownloading){
                    Log.e("--------",current+"///"+total);
                }
                pb_update.setMax((int)total);
                pb_update.setProgress((int)current);
                tv_confirm.setText("下载中");
                tv_confirm.setVisibility(View.INVISIBLE);
            }

        });
    }

    public View.OnClickListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public View.OnClickListener getConfirmListener() {
        return confirmListener;
    }

    public void setConfirmListener(View.OnClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public void setLastVersion(Version lastVersion) {
        this.lastVersion = lastVersion;
    }
}
