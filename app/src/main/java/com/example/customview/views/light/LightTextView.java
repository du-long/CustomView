package com.example.customview.views.light;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class LightTextView extends AppCompatTextView {

    // 光影的左边界
    private int mLeft;
    // 光影移动动画
    // 新图层的画笔
    private Paint mPaint;
    // 图像混合模式，只显示source和dest的重合部分，显示为source的内容
    private PorterDuffXfermode xFermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private boolean start = false;

    public LightTextView(Context context) {
        super(context);
        init();
    }

    public LightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(getPaint());
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        Paint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baselineY = getHeight() / 2f + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float textX = (getWidth() - paint.measureText(text)) / 2;
        canvas.drawText(text, textX, baselineY, paint);

        if (!start) return;
        // 新建一个图层

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawText(text, textX, baselineY, mPaint);
        mPaint.setXfermode(xFermode);
        mPaint.setShader(new LinearGradient(mLeft, 0, mLeft + getWidth() / 3, getHeight(), new int[]{0x00ffffff, 0xffffffff, 0x00ffffff}, null, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setShader(null);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
        if ((mLeft += 6) > getWidth())
            mLeft = -getWidth();
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mLeft = -getWidth();
    }

    public void start() {
        if (start) return;
        start = true;
        mLeft = -getWidth();
        invalidate();
    }

    public void stop() {
        if (!start) return;
        start = false;
        mLeft = -getWidth();
        invalidate();
    }
}
