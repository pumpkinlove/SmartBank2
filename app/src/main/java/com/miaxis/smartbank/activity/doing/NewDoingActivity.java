package com.miaxis.smartbank.activity.doing;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;
import com.miaxis.smartbank.utils.ImageUtil;
import com.miaxis.smartbank.view.BottomMenu;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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

    private int photoNum;

    private BottomMenu bottomMenu;

    private boolean flag = true;

    private View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_menu_1:
                    flag = true;
                    Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(inttPhoto, photoNum + 1);
                    break;
                case R.id.btn_menu_2:
                    flag = false;
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
        bottomMenu = new BottomMenu(this, menuListener);
    }

    @Override
    protected void initView() {
        tvLeft.setVisibility(View.VISIBLE);
        tvMiddle.setVisibility(View.INVISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("上传");
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(flag) {
            if(resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                previewPhotoByNum(requestCode, resultCode, bitmap);
            }
        }else{
            ContentResolver resolver = getContentResolver();
            if(resultCode == Activity.RESULT_OK){
                try {
                    // 获得图片的uri
                    Uri originalUri = data.getData();
                    // 将图片内容解析成字节数组
                    byte[] mContent = ImageUtil.readStream(resolver.openInputStream(Uri
                            .parse(originalUri.toString())));
                    // 将字节数组转换为ImageView可调用的Bitmap对象
                    Bitmap myBitmap = ImageUtil.getPicFromBytes(mContent, null);
                    // //把得到的图片绑定在控件上显示
                    previewPhotoByNum(requestCode, resultCode, myBitmap);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
