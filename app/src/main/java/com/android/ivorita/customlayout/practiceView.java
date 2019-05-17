package com.android.ivorita.customlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.ivorita.customlayout.util.DisplayHelper;

import static com.android.ivorita.customlayout.util.DisplayHelper.dpToPx;
import static com.android.ivorita.customlayout.util.DisplayHelper.sp2px;

public class practiceView extends View {

    //控件宽
    private int width;
    //控件高
    private int height;
    //圆弧半径
    private int arcRadius;
    //总圆弧画笔
    private Paint arcPaint;
    //温度圆弧画笔
    private Paint tempArcPaint;
    //内部数值画笔
    private Paint textPaint;

    private String title = "CO2";

    public practiceView(Context context) {
        super(context);
    }

    public practiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public practiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public practiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(Color.parseColor("#E0E0E0"));
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(dpToPx(5));
        arcPaint.setStyle(Paint.Style.STROKE);

        tempArcPaint = new Paint();
        tempArcPaint.setAntiAlias(true);
        tempArcPaint.setColor(Color.parseColor("#2196F3"));
        tempArcPaint.setStrokeCap(Paint.Cap.ROUND);
        tempArcPaint.setStrokeWidth(dpToPx(5));
        tempArcPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(sp2px(getContext(), 15));
        textPaint.setColor(Color.parseColor("#3B434E"));
        textPaint.setStyle(Paint.Style.FILL);

    }

    /*@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //控件高、宽
        width = height = Math.min(h, w);
        //圆弧半径
        arcRadius = width / 5 - dpToPx(20);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int myWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int myWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int myHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int myHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 获取宽
        if (myWidthSpecMode == MeasureSpec.EXACTLY) {
            // match_parent/精确值
            width = myWidthSpecSize;
        } else {
            // wrap_content
            width = DisplayHelper.dpToPx(150);
        }

        // 获取高
        if (myHeightSpecMode == MeasureSpec.EXACTLY) {
            // match_parent/精确值
            height = myHeightSpecSize;
        } else {
            // wrap_content
            height = DisplayHelper.dpToPx(150);
        }

        arcRadius = width / 2 - dpToPx(20);

        // 设置该view的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();
        drawArc(canvas);
        drawTempArc(canvas);
        drawText(canvas);

    }

    //绘制总圆弧
    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 0, 265, false, arcPaint);
        canvas.restore();
    }

    //绘制总圆弧
    private void drawTempArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 0, 60, false, tempArcPaint);
        canvas.restore();
    }

    //绘制标题
    private void drawText(Canvas canvas) {
        canvas.save();
        //canvas.translate(0, getHeight() / 2);
        float titleWidth = textPaint.measureText(title);
        canvas.drawText(title, (width - titleWidth) / 2, arcRadius * 2 + dpToPx(10), textPaint);
        canvas.restore();
    }
}
