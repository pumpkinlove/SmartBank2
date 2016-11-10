package com.miaxis.smartbank.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.miaxis.smartbank.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/9/21.
 */
public class BottomMenu implements View.OnTouchListener{

    private Activity context;
    private View.OnClickListener listener;

    private View menuView;

    @ViewInject(R.id.btn_menu_1)
    private Button btnMenu1;

    @ViewInject(R.id.btn_menu_2)
    private Button btnMenu2;

    @ViewInject(R.id.btn_menu_3)
    private Button btnMenu3;

    public PopupWindow popupWindow;

    @ViewInject(R.id.pop_layout)
    private LinearLayout pop_layout;

    public BottomMenu(Activity context, View.OnClickListener listener) {

        this.context = context;
        this.listener = listener;

        LayoutInflater inflater = LayoutInflater.from(context);
        menuView = inflater.inflate(R.layout.menu_bottom, null);
        x.view().inject(this, menuView);

        popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOutsideTouchable(true);

        menuView.setOnTouchListener(this);

    }

    public void show(){
        //得到当前activity的rootView
        View rootView=((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {

        int height = pop_layout.getTop();
        int y=(int) event.getY();
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(y<height){
                popupWindow.dismiss();
            }
        }
        return true;
    }

    @Event(value = {R.id.btn_menu_1, R.id.btn_menu_2, R.id.btn_menu_3})
    private void onBtnClicked(View view) {
        listener.onClick(view);
    }

}
