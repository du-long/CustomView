package com.example.customview.views;

import android.widget.TextView;

public interface Item {


    TextView getTextView();

    CharSequence getText();

    void setItemHeight(int pixels);

    int getItemHeight();

    void setIndex(int index);

    int getIndex();

    void onShow();

    void onHint();

    void onSelected();

    void onUnselected();


}
