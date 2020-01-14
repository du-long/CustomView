package com.example.customview.views.drop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class IconView extends View {

    private Paint mPaint;
    private Paint temp;
    private int speed = 10;
    private boolean open;

    public IconView(Context context) {
        this(context, null);
    }

    public IconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStrokeWidth(30);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        temp = new Paint();
        temp.setColor(0xFF000000);
        temp.setStrokeWidth(1.5f);
        temp.setAntiAlias(true);
        temp.setStrokeCap(Paint.Cap.ROUND);
    }

    float degrees = 0;

    float getDrawWidth() {
        return getWidth() - mPaint.getStrokeWidth() * 2f;
    }

    float getDrawHeight() {
        return (getHeight() - mPaint.getStrokeWidth() * 2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 辅助线
//        canvas.drawLine(mPaint.getStrokeWidth(), mPaint.getStrokeWidth(), getWidth() - mPaint.getStrokeWidth(), mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(mPaint.getStrokeWidth(), getHeight() - mPaint.getStrokeWidth(), getWidth() - mPaint.getStrokeWidth(), getHeight() - mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(mPaint.getStrokeWidth(), mPaint.getStrokeWidth(), mPaint.getStrokeWidth(), getHeight() - mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(getWidth() - mPaint.getStrokeWidth(), mPaint.getStrokeWidth(), getWidth() - mPaint.getStrokeWidth(), getHeight() - mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(mPaint.getStrokeWidth(), getHeight() / 2, getWidth() - mPaint.getStrokeWidth(), getHeight() / 2, temp);
//        canvas.drawLine(mPaint.getStrokeWidth(), getDrawHeight() / 4 + mPaint.getStrokeWidth(), getWidth() - mPaint.getStrokeWidth(), getDrawHeight() / 4 + mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(mPaint.getStrokeWidth() + 0, getDrawHeight() / 4 * 3 + mPaint.getStrokeWidth(), getWidth() - mPaint.getStrokeWidth(), getDrawHeight() / 4 * 3 + mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(getWidth() / 2, mPaint.getStrokeWidth(), getWidth() / 2, getHeight() - mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(getDrawWidth() / 4 + mPaint.getStrokeWidth(), mPaint.getStrokeWidth(), getDrawWidth() / 4 + mPaint.getStrokeWidth(), getHeight() - mPaint.getStrokeWidth(), temp);
//        canvas.drawLine(getDrawWidth() / 4 * 3 + mPaint.getStrokeWidth(), mPaint.getStrokeWidth(), getDrawWidth() / 4 * 3 + mPaint.getStrokeWidth(), getHeight() - mPaint.getStrokeWidth(), temp);


        canvas.save();
        canvas.rotate(degrees, getWidth() / 4f + mPaint.getStrokeWidth(), getHeight() / 2);
        canvas.translate(mPaint.getStrokeWidth(), 0);
        canvas.drawLine(mPaint.getStrokeWidth(), getHeight() / 4f + mPaint.getStrokeWidth(), getWidth() / 2f - mPaint.getStrokeWidth(), getHeight() / 4f * 3f - mPaint.getStrokeWidth(), mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(-Math.abs(degrees), getWidth() / 4f * 3 - mPaint.getStrokeWidth(), getHeight() / 2);
        canvas.translate(-mPaint.getStrokeWidth(), 0);
        canvas.drawLine(getWidth() - mPaint.getStrokeWidth(), getHeight() / 4f + mPaint.getStrokeWidth(), getWidth() / 2f + mPaint.getStrokeWidth(), getHeight() / 4f * 3f - mPaint.getStrokeWidth(), mPaint);
        canvas.restore();
        if (open)
            open();
        else
            close();
    }

    public void open() {
        this.open = true;
        if (degrees < 90) {
            degrees += speed;
            invalidate();
        } else if (degrees != 90) {
            degrees = 90;
            invalidate();
        }
    }

    public void close() {
        this.open = false;
        if (degrees > 0) {
            degrees -= speed;
            invalidate();
        } else if (degrees != 0) {
            degrees = 0;
            invalidate();
        }
    }


    public void setIconColor(int color) {
        mPaint.setColor(color);
    }

    public void setIconWidth(float width) {
        mPaint.setStrokeWidth(width);
    }

}
