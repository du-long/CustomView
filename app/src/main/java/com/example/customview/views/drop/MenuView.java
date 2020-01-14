package com.example.customview.views.drop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class MenuView extends LinearLayout implements ItemFactory, ItemWindow.OnItemClickListener, PopupWindow.OnDismissListener {

    private TextSwitcher mTitleView;
    private AttributeSet attrs;
    private ItemWindow mItemWindow;

    private int mTextColor = 0XFFFFFFFF;
    private float mTextSize = 20;
    private float mItemTextSize = 20;
    private int mItemTextolor = 0XFFFFFFFF;
    private int mItemHeight = 100;
    private int mItemPaddingStart;
    private int mItemPaddingEnd;
    private int mItemPaddingTop;
    private int mItemPaddingBottom;

    private Drawable mSelectedBackground;
    private OnSelectedListenere mOnSelectedListenere;
    private int mIconSize;
    private IconView mIconView;

    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setEnabled(false);
        this.attrs = attrs;
        initAttrs(context);
        addTitleView(context);
        addIconView(context);
        attachItemWindow(context, attrs);

    }

    private void addIconView(Context context) {

        mIconView = new IconView(context);
        LayoutParams params = new LayoutParams(mIconSize, mIconSize);
        params.rightMargin =20;
        params.setMarginEnd(20);
        params.gravity = Gravity.CENTER_VERTICAL;
        mIconView.setLayoutParams(params);
        mIconView.setIconColor(0xFFFFFFFF);
        mIconView.setIconWidth(6);
        addView(mIconView);

    }

    private void initAttrs(Context context) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MenuView);

        mIconSize = ta.getDimensionPixelSize(R.styleable.MenuView_iconSize, 0);
        mTextColor = ta.getColor(R.styleable.MenuView_textColor, mTextColor);
        mTextSize = ta.getDimension(R.styleable.MenuView_textSize, mTextSize);

        mItemTextolor = ta.getColor(R.styleable.MenuView_itemTextColor, 0xffffffff);
        mItemTextSize = ta.getDimension(R.styleable.MenuView_itemTextSize, mItemTextSize);
        mItemHeight = ta.getDimensionPixelSize(R.styleable.MenuView_itemHeight, mItemHeight);

        mItemPaddingStart = ta.getDimensionPixelSize(R.styleable.MenuView_itemPaddingStart, 0);
        mItemPaddingEnd = ta.getDimensionPixelSize(R.styleable.MenuView_itemPaddingEnd, 0);
        mItemPaddingTop = ta.getDimensionPixelSize(R.styleable.MenuView_itemPaddingTop, 0);
        mItemPaddingBottom = ta.getDimensionPixelSize(R.styleable.MenuView_itemPaddingBottom, 0);
        mSelectedBackground = ta.getDrawable(R.styleable.MenuView_itemBackground);
        ta.recycle();
    }


    @Override
    public boolean performClick() {
        if (mItemWindow.isShowing())
            mItemWindow.dismiss();
        else {
            show();
            setSelected(true);
            mIconView.open();
        }
        return super.performClick();
    }

    private void attachItemWindow(Context context, AttributeSet attrs) {
        mItemWindow = new ItemWindow(context, attrs);
        mItemWindow.setOnItemClickListener(this);
        mItemWindow.setItemFactory(this);
        mItemWindow.setOnDismissListener(this);
    }

    /**
     * 设置文字
     *
     * @param text view 文字
     */
    public void setText(CharSequence text) {
        ((TextView) mTitleView.getChildAt(mTitleView.getDisplayedChild() == 1 ? 1 : 0)).setText(text);
    }

    /**
     * 设置文字
     *
     * @param text 文字
     * @param anim 是否执行动画
     */
    public void setText(CharSequence text, boolean anim) {
        if (anim)
            mTitleView.setText(text);
        else setText(text);
    }

    public void setItems(CharSequence[] items) {
        mItemWindow.setItems(items);
    }

    public void show() {
        mItemWindow.show(this);
    }


    public final void addTitleView(Context context) {
        mTitleView = new TextSwitcher(context);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mTitleView.setLayoutParams(layoutParams);
        mTitleView.setInAnimation(createInAnim());
        mTitleView.setOutAnimation(createOutAnim());


        mTitleView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return createView();
            }
        });
        addView(mTitleView);
    }


    public TextView createView() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(mTextSize);
        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        textView.setTextColor(mTextColor);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public Animation createInAnim() {
        TranslateAnimation inAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0);
        inAnim.setDuration(150);
        return inAnim;
    }

    public Animation createOutAnim() {
        TranslateAnimation outAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1);
        outAnim.setDuration(150);
        return outAnim;
    }


    @Override
    public Item createItemView() {
        ItemView itemView = new ItemView(getContext());
        itemView.getTextView().setTextColor(mItemTextolor);

        itemView.setPadding(mItemPaddingStart, mItemPaddingTop, mItemPaddingEnd, mItemPaddingBottom);
        itemView.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, mItemTextSize);
        itemView.setItemHeight(mItemHeight);
        itemView.setForeground(getResources().getDrawable(R.drawable.menu_ripple_foreground));

        itemView.setSelectedBackground(mSelectedBackground);
        return itemView;
    }


    @Override
    public void onItemClick(Item item, int index, CharSequence text) {
        setText(text, true);
        mIconView.close();
        if (mOnSelectedListenere != null)
            mOnSelectedListenere.onSelected(this, index, text);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mItemWindow.dismiss();
            }
        }, 400);
    }

    public void setOnSelectedListenere(OnSelectedListenere onSelectedListenere) {
        this.mOnSelectedListenere = onSelectedListenere;
    }

    public int getSelectIndex() {
        return mItemWindow.getSelectIndex();
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        setEnabled(clickable);
    }

    @Override
    public void onDismiss() {
        setSelected(mItemWindow.getSelectIndex() != -1);
        mIconView.close();
    }

    public interface OnSelectedListenere {
        void onSelected(MenuView menuView, int index, CharSequence text);
    }
}
