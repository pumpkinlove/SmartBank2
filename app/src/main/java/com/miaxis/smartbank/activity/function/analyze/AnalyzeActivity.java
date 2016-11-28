package com.miaxis.smartbank.activity.function.analyze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

//@ContentView(R.layout.activity_analyze)
public class AnalyzeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new CustomView(this));
//        x.view().inject(this);

//        initData();
//        initView();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    class CustomView extends View {
        Paint paint;

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(100, 100, 90, paint);
            paint.setColor(Color.BLACK);
            RectF r = new RectF(0, 0, 200 , 200);
            canvas.drawArc(r, 0, 90, false, paint);
            canvas.drawRect(r, paint);
        }

        public CustomView(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);



        }
    }
}
