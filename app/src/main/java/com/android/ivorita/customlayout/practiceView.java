package com.android.ivorita.customlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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
    //内部标题画笔
    private Paint titlePaint;
    //内部数值画笔
    private Paint valuePaint;
    //标题
    private String title = "温度";
    //温度
    private int temperature = 16;
    //最高温度
    private int maxTemp = 15;
    //最低温度
    private int minTemp = 30;
    //4格代表温度1度
    private int angleRate = 4;
    //每格的角度
    private float anglePer = (float) 270 / (maxTemp - minTemp) / angleRate;
    //温度改变监听接口
    private OnTempChangeListener onTempChangeListener;

    //按钮旋转的角度
    private float rotateAngle;
    //当前角度
    private float currentAngle;

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

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(sp2px(getContext(), 15));
        titlePaint.setColor(Color.parseColor("#3B434E"));
        titlePaint.setStyle(Paint.Style.FILL);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setTextSize(sp2px(getContext(), 18));
        valuePaint.setColor(Color.parseColor("#E27A3F"));
        valuePaint.setStyle(Paint.Style.FILL);

    }

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
        drawTitle(canvas);
        drawValue(canvas);
    }

    /**
     * 绘制总圆弧
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 0, 270, false, arcPaint);
        canvas.restore();
    }

    /**
     * 绘制温度值圆弧
     */
    private void drawTempArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 0, (temperature - minTemp) * angleRate * anglePer, false, tempArcPaint);
        canvas.restore();
    }

    /**
     * 绘制标题与温度标识
     */
    private void drawTitle(Canvas canvas) {
        canvas.save();

        //绘制标题
        float titleWidth = titlePaint.measureText(title);
        canvas.drawText(title, (width - titleWidth) / 2, arcRadius * 2 + dpToPx(10), titlePaint);

        canvas.restore();
    }

    private void drawValue(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);

        float valueWidth = valuePaint.measureText(temperature + "℃");
        //1.基准点是baseline
        //2.ascent：是baseline之上至字符最高处的距离
        // 3.descent：是baseline之下至字符最低处的距离*/
        float valueHeight = (valuePaint.ascent() + valuePaint.descent()) / 2;
        canvas.drawText(temperature + "℃", -valueWidth / 2, -valueHeight, valuePaint);

        canvas.restore();
    }

    /**
     * 温度改变监听接口
     */
    public interface OnTempChangeListener {
        /**
         * @param temp 温度
         */
        void change(int temp);
    }

    /**
     * 设置温度改变监听接口
     *
     * @param onTempChangeListener 温度改变监听接口
     */
    public void setOnTempChangeListener(OnTempChangeListener onTempChangeListener) {
        this.onTempChangeListener = onTempChangeListener;
    }

    /**
     * 设置温度
     *
     * @param temp 设置的温度
     */
    public void setTemp(int temp) {
        setTemp(minTemp, maxTemp, temp);
    }


    /**
     * 设置温度
     *
     * @param minTemp 最低温度
     * @param maxTemp 最高温度
     * @param temp    设置的温度
     */
    public void setTemp(int minTemp, int maxTemp, int temp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;

        if (temp < minTemp) {
            this.temperature = minTemp;
        } else {
            this.temperature = temp;
        }
        //每格的角度
        anglePer = (float) 270 / (maxTemp - minTemp) / angleRate;
        //计算旋转角度
        rotateAngle = (temp - minTemp) * angleRate * anglePer;


        Log.d("c_view", "temp: " + temp);
        Log.d("c_view", "minTemp: " + minTemp);
        Log.d("c_view", "angleRate: " + angleRate);
        Log.d("c_view", "anglePer: " + anglePer);
        Log.d("c_view", "rotateAngle: " + rotateAngle);
        //刷新视图
        invalidate();
    }

    public void setAngleRate(int angleRate) {
        this.angleRate = angleRate;
    }
}
