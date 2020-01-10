package com.example.customview.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ItemView extends LinearLayout implements Item, Animation.AnimationListener {

    private TextView mTextView;
    private int index;
    private Animation mAnimation;
    private static final long ANIMATION_TIME = 100;
    private Drawable mSelectedBackground;
    private int mItemHeight;

    public ItemView(Context context) {
        super(context);
        mTextView = new TextView(context);

        mTextView.setGravity(Gravity.CENTER_VERTICAL);
        addView(mTextView);
        mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        mAnimation = new AlphaAnimation(0.0f, 1.0f);
        ;
        mAnimation.setDuration(200);
        mAnimation.setAnimationListener(this);
    }

    public void setSelectedBackground(Drawable selectedDrawable) {
        mSelectedBackground = selectedDrawable;

    }


    @Override
    public TextView getTextView() {
        return mTextView;
    }

    @Override
    public CharSequence getText() {
        return mTextView.getText();
    }

    @Override
    public void setItemHeight(int pixels) {
        mItemHeight = pixels;
        mTextView.setHeight(pixels);
    }

    @Override
    public int getItemHeight() {
        return mItemHeight;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }


    @Override
    public void onShow() {
        mAnimation.cancel();
        mTextView.setVisibility(INVISIBLE);
        removeCallbacks(null);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextView.startAnimation(mAnimation);
            }
        }, ANIMATION_TIME * (getIndex() + 1));
    }

    @Override
    public void onHint() {

    }


    @Override
    public void onSelected() {

        setBackground(mSelectedBackground);
    }

    @Override
    public void onUnselected() {

        setBackground(null);
    }


    @Override
    public void onAnimationStart(Animation animation) {
        mTextView.setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
