package com.miaxis.smartbank.activity.function.doing;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.BankDoing;
import com.miaxis.smartbank.domain.event.RefreshNewDoingsEvent;
import com.miaxis.smartbank.utils.CommonUtil;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.DateUtil;
import com.miaxis.smartbank.utils.XUtil;
import com.miaxis.smartbank.view.BottomMenu;
import com.miaxis.smartbank.view.ImageDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xiaofei.library.hermeseventbus.HermesEventBus;

@ContentView(R.layout.activity_new_doing)
public class NewDoingActivity extends BaseActivity {

    @ViewInject(R.id.iv_photo0)
    private ImageView ivPhoto0;

    @ViewInject(R.id.iv_photo1)
    private ImageView ivPhoto1;

    @ViewInject(R.id.iv_photo2)
    private ImageView ivPhoto2;

    @ViewInject(R.id.iv_photo3)
    private ImageView ivPhoto3;

    @ViewInject(R.id.iv_photo4)
    private ImageView ivPhoto4;

    @ViewInject(R.id.iv_photo5)
    private ImageView ivPhoto5;

    @ViewInject(R.id.iv_photo6)
    private ImageView ivPhoto6;

    @ViewInject(R.id.iv_photo7)
    private ImageView ivPhoto7;

    @ViewInject(R.id.iv_photo8)
    private ImageView ivPhoto8;

    @ViewInject(R.id.iv_add1)
    private ImageView ivAdd1;

    @ViewInject(R.id.iv_add2)
    private ImageView ivAdd2;

    @ViewInject(R.id.iv_add3)
    private ImageView ivAdd3;

    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    @ViewInject(R.id.tv_right)
    private TextView tvRight;

    @ViewInject(R.id.tv_content)
    private TextView tvContent;

    private int photoNum;

    private BottomMenu bottomMenu;

    private boolean flag = true;            //true 拍照  false从相册中选取

    private BankDoing bankDoing;

    private ImageDialog imageDialog;

    private String filePathCache = "";

    private List<String> urlList;

    private View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_menu_1:
                    flag = true;
                    bottomMenu.popupWindow.dismiss();
                    filePathCache = Environment.getExternalStorageDirectory() + "/bankdoing/" + new Date().getTime() + ".jpg";
                    File vFile = new File(filePathCache);

                    if (!vFile.exists()) {
                        File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
                        vDirPath.mkdirs();
                    }
                    Uri uri = Uri.fromFile(vFile);
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
                    startActivityForResult(i, photoNum = photoNum + 1);
                    break;
                case R.id.btn_menu_2:
                    flag = false;
                    bottomMenu.popupWindow.dismiss();
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, photoNum = photoNum + 1);
                    break;
                case R.id.btn_menu_3:
                    bottomMenu.popupWindow.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        HermesEventBus.getDefault().register(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        urlList = new ArrayList<>();
        photoNum = 0;
        bankDoing = new BankDoing();
        bottomMenu = new BottomMenu(this, menuListener);
        imageDialog = new ImageDialog();
    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setVisibility(View.INVISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("上传");
        tvRight.setTextColor(getResources().getColor(R.color.green_dark));
    }

    @Event(R.id.tv_left)
    private void back(View view) {
        finish();
    }

    @Event(value = {R.id.iv_add1, R.id.iv_add2, R.id.iv_add3})
    private void addNewDoing(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        bottomMenu.show();

    }

    @Event(value = {R.id.iv_photo0, R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4, R.id.iv_photo5, R.id.iv_photo6, R.id.iv_photo7, R.id.iv_photo8})
    private void previewPhoto(View view) {
        switch (view.getId()) {
            case R.id.iv_photo0:
                imageDialog.setUrl(bankDoing.getPhoto0());
                break;
            case R.id.iv_photo1:
                imageDialog.setUrl(bankDoing.getPhoto1());
                break;
            case R.id.iv_photo2:
                imageDialog.setUrl(bankDoing.getPhoto2());
                break;
            case R.id.iv_photo3:
                imageDialog.setUrl(bankDoing.getPhoto3());
                break;
            case R.id.iv_photo4:
                imageDialog.setUrl(bankDoing.getPhoto4());
                break;
            case R.id.iv_photo5:
                imageDialog.setUrl(bankDoing.getPhoto5());
                break;
            case R.id.iv_photo6:
                imageDialog.setUrl(bankDoing.getPhoto6());
                break;
            case R.id.iv_photo7:
                imageDialog.setUrl(bankDoing.getPhoto7());
                break;
            case R.id.iv_photo8:
                imageDialog.setUrl(bankDoing.getPhoto8());
                break;
        }
        imageDialog.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlList.remove(imageDialog.getUrl());
                HermesEventBus.getDefault().post(new RefreshNewDoingsEvent());
                imageDialog.dismiss();
            }
        });
        imageDialog.setButtonFlag(true);
        imageDialog.show(getFragmentManager(), "PREVIEW_BIG");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (flag) {
            if (resultCode == Activity.RESULT_OK) {
                uploadPhoto(requestCode , new File(filePathCache) );
            }
        } else {
            ContentResolver resolver = getContentResolver();
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 获得图片的uri
                    Uri originalUri = data.getData();
                    uploadPhoto(requestCode, CommonUtil.getFileByUri(originalUri, this));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Event(R.id.tv_right)
    private void upload(View view) {
        bankDoing.setContent(tvContent.getText().toString());
        bankDoing.setOrganname(MyApplication.config.getOrganname());
        bankDoing.setOrganid(MyApplication.config.getOrganid());
        String url = MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" + Constant.ADD_DOING;

        Map<String, Object> params = new HashMap<>();

        params.put("content",   bankDoing.getContent());
        params.put("opdate",    bankDoing.getOpdate());
        params.put("optime",    bankDoing.getOptime());
        params.put("organid",   bankDoing.getOrganid());
        params.put("organname", bankDoing.getOrganname());
        params.put("photo0",    bankDoing.getPhoto0());
        params.put("photo1",    bankDoing.getPhoto1());
        params.put("photo2",    bankDoing.getPhoto2());
        params.put("photo3",    bankDoing.getPhoto3());
        params.put("photo4",    bankDoing.getPhoto4());
        params.put("photo5",    bankDoing.getPhoto5());
        params.put("photo6",    bankDoing.getPhoto6());
        params.put("photo7",    bankDoing.getPhoto7());
        params.put("photo8",    bankDoing.getPhoto8());

        XUtil.Post(url, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("result", result);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);
                JsonObject o = element.getAsJsonObject();
                boolean success = o.get("success").getAsBoolean();
                if (!success) {
                    CommonUtil.alert(getFragmentManager(), "与服务器通讯失败");
                    return;
                }
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("ex", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    private void uploadPhoto(final int index, File file) {
        Log.e("uploadPhoto", DateUtil.toHourMinString(new Date()) + "   =======");
        Map<String, Object> params = new HashMap<>();
        params.put("uploadify", file);
        String url = MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" + Constant.UPLOAD_PHOTO;
        XUtil.UpLoadFile(url, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess", DateUtil.toHourMinString(new Date()) + "   =======");
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);
                JsonObject o = element.getAsJsonObject();
                boolean success = o.get("success").getAsBoolean();
                if (!success) {
                    CommonUtil.alert(getFragmentManager(), "与服务器通讯失败");
                    return;
                }
                String path = o.get("path").getAsString() + "/" + o.get("newFileName").getAsString();
                urlList.add(path);
                HermesEventBus.getDefault().post(new RefreshNewDoingsEvent());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CommonUtil.alert(getFragmentManager(), "ex" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void refreshList(RefreshNewDoingsEvent event) {
        Log.e("refreshList", DateUtil.toHourMinString(new Date()) + "   =======");
        switch (urlList.size()) {
            case 0:
                bankDoing.setPhoto0(null);
                bankDoing.setPhoto1(null);
                bankDoing.setPhoto2(null);
                bankDoing.setPhoto3(null);
                bankDoing.setPhoto4(null);
                bankDoing.setPhoto5(null);
                bankDoing.setPhoto6(null);
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;

            case 1:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(null);
                bankDoing.setPhoto2(null);
                bankDoing.setPhoto3(null);
                bankDoing.setPhoto4(null);
                bankDoing.setPhoto5(null);
                bankDoing.setPhoto6(null);
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;
            case 2:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(null);
                bankDoing.setPhoto3(null);
                bankDoing.setPhoto4(null);
                bankDoing.setPhoto5(null);
                bankDoing.setPhoto6(null);
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;
            case 3:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(urlList.get(2));
                bankDoing.setPhoto3(null);
                bankDoing.setPhoto4(null);
                bankDoing.setPhoto5(null);
                bankDoing.setPhoto6(null);
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;
            case 4:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(urlList.get(2));
                bankDoing.setPhoto3(urlList.get(3));
                bankDoing.setPhoto4(null);
                bankDoing.setPhoto5(null);
                bankDoing.setPhoto6(null);
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;
            case 5:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(urlList.get(2));
                bankDoing.setPhoto3(urlList.get(3));
                bankDoing.setPhoto4(urlList.get(4));
                bankDoing.setPhoto5(null);
                bankDoing.setPhoto6(null);
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;
            case 6:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(urlList.get(2));
                bankDoing.setPhoto3(urlList.get(3));
                bankDoing.setPhoto4(urlList.get(4));
                bankDoing.setPhoto5(urlList.get(5));
                bankDoing.setPhoto6(null);
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;
            case 7:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(urlList.get(2));
                bankDoing.setPhoto3(urlList.get(3));
                bankDoing.setPhoto4(urlList.get(4));
                bankDoing.setPhoto5(urlList.get(5));
                bankDoing.setPhoto6(urlList.get(6));
                bankDoing.setPhoto7(null);
                bankDoing.setPhoto8(null);

                break;
            case 8:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(urlList.get(2));
                bankDoing.setPhoto3(urlList.get(3));
                bankDoing.setPhoto4(urlList.get(4));
                bankDoing.setPhoto5(urlList.get(5));
                bankDoing.setPhoto6(urlList.get(6));
                bankDoing.setPhoto7(urlList.get(7));
                bankDoing.setPhoto8(null);

                break;
            case 9:
                bankDoing.setPhoto0(urlList.get(0));
                bankDoing.setPhoto1(urlList.get(1));
                bankDoing.setPhoto2(urlList.get(2));
                bankDoing.setPhoto3(urlList.get(3));
                bankDoing.setPhoto4(urlList.get(4));
                bankDoing.setPhoto5(urlList.get(5));
                bankDoing.setPhoto6(urlList.get(6));
                bankDoing.setPhoto7(urlList.get(7));
                bankDoing.setPhoto8(urlList.get(8));

                break;

        }

        ivAdd1.setVisibility(View.GONE);
        ivAdd2.setVisibility(View.GONE);
        ivAdd3.setVisibility(View.GONE);

        Log.e("setphoto", DateUtil.toHourMinString(new Date()) + "   =======");
        ImageOptions options = new ImageOptions.Builder()
                .setFadeIn(true)
                .setUseMemCache(true)//设置使用缓存
                .setRadius(2)
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
                // 图片缩放模式
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                // 下载中显示的图片
                        .setLoadingDrawableId(R.mipmap.upload)
                // 下载失败显示的图片
                        .setFailureDrawableId(R.mipmap.failpic)
                // 得到ImageOptions对象
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_CENTER)
                .build();

        Log.e("getPhoto0", DateUtil.toHourMinString(new Date()) + "   =======");
        if (bankDoing.getPhoto0() != null && bankDoing.getPhoto0().length() > 0) {
            x.image().bind(ivPhoto0, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" + bankDoing.getPhoto0(), options, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                    Log.e("onSuccess =============", DateUtil.toHourMinString(new Date()) + "   =======");
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
            ivPhoto0.setVisibility(View.VISIBLE);
        } else {
            ivPhoto0.setVisibility(View.GONE);
            ivAdd1.setVisibility(View.VISIBLE);
            return;
        }
        Log.e("setphoto", DateUtil.toHourMinString(new Date()) + "   =======");
        if (bankDoing.getPhoto1() != null && bankDoing.getPhoto1().length() > 0) {
            x.image().bind(ivPhoto1, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto1(), options);
            ivPhoto1.setVisibility(View.VISIBLE);
        } else {
            ivPhoto1.setVisibility(View.GONE);
            ivAdd1.setVisibility(View.VISIBLE);
            return;
        }

        if (bankDoing.getPhoto2() != null && bankDoing.getPhoto2().length() > 0) {
            x.image().bind(ivPhoto2, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto2(), options);
            ivPhoto2.setVisibility(View.VISIBLE);
        } else {
            ivPhoto2.setVisibility(View.GONE);
            ivAdd1.setVisibility(View.VISIBLE);
            return;
        }

        if (bankDoing.getPhoto3() != null && bankDoing.getPhoto3().length() > 0) {
            x.image().bind(ivPhoto3, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto3(), options);
            ivPhoto3.setVisibility(View.VISIBLE);
        } else {
            ivPhoto3.setVisibility(View.GONE);
            ivAdd2.setVisibility(View.VISIBLE);
            return;
        }

        if (bankDoing.getPhoto4() != null && bankDoing.getPhoto4().length() > 0) {
            x.image().bind(ivPhoto4, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto4(), options);
            ivPhoto4.setVisibility(View.VISIBLE);
        } else {
            ivPhoto4.setVisibility(View.GONE);
            ivAdd2.setVisibility(View.VISIBLE);
            return;
        }

        if (bankDoing.getPhoto5() != null && bankDoing.getPhoto5().length() > 0) {
            x.image().bind(ivPhoto5, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto5(), options);
            ivPhoto5.setVisibility(View.VISIBLE);
        } else {
            ivPhoto5.setVisibility(View.GONE);
            ivAdd2.setVisibility(View.VISIBLE);
            return;
        }

        if (bankDoing.getPhoto6() != null && bankDoing.getPhoto6().length() > 0) {
            x.image().bind(ivPhoto6, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto6(), options);
            ivPhoto6.setVisibility(View.VISIBLE);
        } else {
            ivPhoto6.setVisibility(View.GONE);
            ivAdd3.setVisibility(View.VISIBLE);
            return;
        }

        if (bankDoing.getPhoto7() != null && bankDoing.getPhoto7().length() > 0) {
            x.image().bind(ivPhoto7, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto7(), options);
            ivPhoto7.setVisibility(View.VISIBLE);
        } else {
            ivPhoto7.setVisibility(View.GONE);
            ivAdd3.setVisibility(View.VISIBLE);
            return;
        }

        if (bankDoing.getPhoto8() != null && bankDoing.getPhoto8().length() > 0) {
            x.image().bind(ivPhoto8, MyApplication.config.getUrl() + "/" + Constant.PROJECT_NAME + "/" +  bankDoing.getPhoto8(), options);
            ivPhoto8.setVisibility(View.VISIBLE);
            ivAdd3.setVisibility(View.GONE);
        } else {
            ivPhoto8.setVisibility(View.GONE);
            ivAdd3.setVisibility(View.VISIBLE);
            return;
        }
        Log.e("setphoto", DateUtil.toHourMinString(new Date()) + "   =======");

    }

    @Override
    protected void onDestroy() {
        HermesEventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
