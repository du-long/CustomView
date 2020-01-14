package com.example.customview.views.drop;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.customview.R;

public class ItemWindow extends PopupWindow implements Animation.AnimationListener, View.OnClickListener {


    private LinearLayout mRootView;
    private ItemFactory itemFactory;
    private Animation mDownShow;
    private Animation mDownHint;
    private Animation mTopShow;
    private Animation mTopHint;
    private int mWindowHeight;
    private int mStatusbarHeight;
    private int mNavigationBarHeight;

    /**
     * 是否正在关闭中
     */
    private boolean closing = false;
    private boolean click = true;
    private int mBackground;
    private int mDistance;


    public ItemWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDisplay(context);
        initAttrs(context, attrs);
        initAnim();
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MenuView);
        mBackground = ta.getResourceId(R.styleable.MenuView_windowBackgroud, 0);
        mDistance = ta.getDimensionPixelSize(R.styleable.MenuView_distance, 0);
        ta.recycle();
    }

    private void initDisplay(Context context) {
        Display defaultDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        mWindowHeight = point.y;
        Resources resources = context.getResources();
        int statusBararId = resources.getIdentifier("status_bar_height", "dimen", "android");
        mStatusbarHeight = resources.getDimensionPixelSize(statusBararId);
        int navigationBarId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        mNavigationBarHeight = resources.getDimensionPixelSize(navigationBarId);
    }

    private void initView(Context context) {
//        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        mRootView = new LinearLayout(context);
        mRootView.setOrientation(LinearLayout.VERTICAL);
        mRootView.setBackgroundResource(mBackground);
        setContentView(mRootView);
    }


    private void initAnim() {
        mDownShow = createVerticalAnimation(-1, 0);
        mDownHint = createVerticalAnimation(0, -1);
        mDownHint.setAnimationListener(this);

        mTopShow = createVerticalAnimation(1, 0);
        mTopHint = createVerticalAnimation(0, 1);
        mTopHint.setAnimationListener(this);

    }


    private Animation createVerticalAnimation(float fromY, float toY) {
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, fromY,
                Animation.RELATIVE_TO_SELF, toY);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

    public void setItemFactory(ItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }


    public void setItems(CharSequence[] items) {
        mRootView.removeAllViews();
        selectIndex = -1;
        for (CharSequence item : items) {
            Item itemView = itemFactory.createItemView();
            itemView.getTextView().setText(item);
            mRootView.addView((View) itemView);
            ((View) itemView).setOnClickListener(this);
        }
    }

    private int getRootHeight() {
        int height = 0;
        for (int i = 0; i < mRootView.getChildCount(); i++)
            height += ((Item) mRootView.getChildAt(i)).getItemHeight();
        return height;
    }

    private Animation mHideAnim;

    public void show(View view) {
        setWidth(view.getWidth());
        if (mWindowHeight - view.getY() - view.getHeight() - getRootHeight() - mDistance * 2 - mStatusbarHeight - mNavigationBarHeight > 0) {
            mRootView.startAnimation(mDownShow);
            showAsDropDown(view, 0, mDistance);
            mHideAnim = mDownHint;
        } else {
            mRootView.startAnimation(mTopShow);
            mHideAnim = mTopHint;
            showAsDropDown(view, 0, -view.getHeight() - getRootHeight() - mDistance);
        }
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            View childAt = mRootView.getChildAt(i);
            if (childAt instanceof Item) {
                ((Item) childAt).setIndex(i);
                ((Item) childAt).onShow();
            }
        }
    }


    @Override
    public void dismiss() {
        if (!closing)
            mRootView.startAnimation(mHideAnim);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        closing = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        super.dismiss();
        click = true;
        closing = false;

    }


    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    private int selectIndex = -1;

    @Override
    public void onClick(View v) {
        if (v instanceof Item && click) {
            click = false;
            for (int i = 0; i < mRootView.getChildCount(); i++) {
                View childAt = mRootView.getChildAt(i);
                if (childAt instanceof Item) {
                    if (v == childAt) {
                        selectIndex = i;
                        ((Item) childAt).onSelected();
                    } else
                        ((Item) childAt).onUnselected();
                }
            }
            if (onItemClickListener != null)
                onItemClickListener.onItemClick((Item) v, ((Item) v).getIndex(), ((Item) v).getText());
        }
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(Item item, int index, CharSequence text);
    }
}
