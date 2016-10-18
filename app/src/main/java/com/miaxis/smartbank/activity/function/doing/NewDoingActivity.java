package com.miaxis.smartbank.activity.function.doing;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.miaxis.smartbank.domain.BankDoing;
import com.miaxis.smartbank.utils.CommonUtil;
import com.miaxis.smartbank.utils.Constant;
import com.miaxis.smartbank.utils.ImageUtil;
import com.miaxis.smartbank.utils.XUtil;
import com.miaxis.smartbank.view.BottomMenu;
import com.miaxis.smartbank.view.ImageDialog;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_menu_1:
                    flag = true;
                    bottomMenu.popupWindow.dismiss();
//                    Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(inttPhoto, photoNum + 1);
                    filePathCache = Environment.getExternalStorageDirectory() + "/bankdoing/" + new Date().getTime() + ".jpg";
                    File vFile = new File(filePathCache);

                    if (!vFile.exists()) {
                        File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
                        vDirPath.mkdirs();
                    }
                    Uri uri = Uri.fromFile(vFile);
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
                    startActivityForResult(i, photoNum + 1);
                    break;
                case R.id.btn_menu_2:
                    flag = false;
                    bottomMenu.popupWindow.dismiss();
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, photoNum + 1);
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
        initData();
        initView();

    }

    @Override
    protected void initData() {
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
        imageDialog.setButtonFlag(true);
        imageDialog.show(getFragmentManager(), "PREVIEW_BIG");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (flag) {
            if (resultCode == Activity.RESULT_OK) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                previewPhotoByNum(requestCode, resultCode, bitmap);



                uploadPhoto(requestCode , new File(filePathCache) );

            }
        } else {
            ContentResolver resolver = getContentResolver();
            if(resultCode == Activity.RESULT_OK){
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

        String url = "http://192.168.5.96:8080/" + Constant.PROJECT_NAME + "/" + Constant.ADD_DOING;

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
        Map<String, Object> params = new HashMap<>();
        params.put("uploadify", file);
        String url = "http://192.168.5.96:8080/" + Constant.PROJECT_NAME + "/" + Constant.UPLOAD_PHOTO;
        XUtil.UpLoadFile(url, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);
                JsonObject o = element.getAsJsonObject();
                boolean success = o.get("success").getAsBoolean();
                if (!success) {
                    CommonUtil.alert(getFragmentManager(), "与服务器通讯失败");
                    return;
                }
                String path = "http://192.168.5.96:8080/" + Constant.PROJECT_NAME + "/" +  o.get("path").getAsString() + "/" + o.get("newFileName").getAsString();
                switch (index - 1) {
                    case 0:
                        bankDoing.setPhoto0(path);
                        x.image().bind(ivPhoto0, path);
                        ivPhoto0.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        bankDoing.setPhoto1(path);
                        x.image().bind(ivPhoto1, path);
                        ivPhoto1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        bankDoing.setPhoto2(path);
                        x.image().bind(ivPhoto2, path);
                        ivPhoto2.setVisibility(View.VISIBLE);
                        ivAdd1.setVisibility(View.GONE);
                        break;
                    case 3:
                        bankDoing.setPhoto3(path);
                        x.image().bind(ivPhoto3, path);
                        ivPhoto3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        bankDoing.setPhoto4(path);
                        x.image().bind(ivPhoto4, path);
                        ivPhoto4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        bankDoing.setPhoto5(path);
                        x.image().bind(ivPhoto5, path);
                        ivPhoto5.setVisibility(View.VISIBLE);
                        ivAdd2.setVisibility(View.GONE);
                        break;
                    case 6:
                        bankDoing.setPhoto6(path);
                        x.image().bind(ivPhoto6, path);
                        ivPhoto6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        bankDoing.setPhoto7(path);
                        x.image().bind(ivPhoto7, path);
                        ivPhoto7.setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        bankDoing.setPhoto8(path);
                        x.image().bind(ivPhoto8, path);
                        ivPhoto8.setVisibility(View.VISIBLE);
                        ivAdd3.setVisibility(View.GONE);
                        break;
                }
                photoNum ++ ;
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

    private void previewPhotoByNum(int n, int resultCode, Bitmap bitmap) {
        switch (n) {
            case 1:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto0.setImageBitmap(bitmap);
                    ivPhoto0.setVisibility(View.VISIBLE);
                    photoNum = 1;
                }
                break;
            case 2:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto1.setImageBitmap(bitmap);
                    ivPhoto1.setVisibility(View.VISIBLE);
                    photoNum = 2;
                }
                break;
            case 3:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto2.setImageBitmap(bitmap);
                    ivPhoto2.setVisibility(View.VISIBLE);
                    ivAdd1.setVisibility(View.GONE);
                    ivAdd2.setVisibility(View.VISIBLE);
                    photoNum = 3;
                }
                break;
            case 4:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto3.setImageBitmap(bitmap);
                    ivPhoto3.setVisibility(View.VISIBLE);
                    photoNum = 4;
                }
                break;
            case 5:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto4.setImageBitmap(bitmap);
                    ivPhoto4.setVisibility(View.VISIBLE);
                    photoNum = 5;
                }
                break;
            case 6:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto5.setImageBitmap(bitmap);
                    ivPhoto5.setVisibility(View.VISIBLE);
                    ivAdd2.setVisibility(View.GONE);
                    ivAdd3.setVisibility(View.VISIBLE);
                    photoNum = 6;
                }
                break;
            case 7:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto6.setImageBitmap(bitmap);
                    ivPhoto6.setVisibility(View.VISIBLE);
                    photoNum = 7;
                }
                break;
            case 8:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto7.setImageBitmap(bitmap);
                    ivPhoto7.setVisibility(View.VISIBLE);
                    photoNum = 8;
                }
                break;
            case 9:
                if(resultCode == Activity.RESULT_OK){
                    ivPhoto8.setImageBitmap(bitmap);
                    ivPhoto8.setVisibility(View.VISIBLE);
                    photoNum = 9;
                    ivAdd3.setVisibility(View.GONE);
                }
                break;
        }
    }


}
