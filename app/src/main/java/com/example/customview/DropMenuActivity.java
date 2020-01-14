package com.example.customview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.R;
import com.example.customview.views.drop.MenuView;

public class DropMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private MenuView mMenuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_menu);
        mMenuView = findViewById(R.id.mv_1);
        mMenuView.setItems(new CharSequence[]{"北京", "上海", "深证", "广州"});
        findViewById(R.id.btn_disabled).setOnClickListener(this);
        findViewById(R.id.btn_enabled).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enabled:
                mMenuView.setClickable(true);
                break;
            case R.id.btn_disabled:
                mMenuView.setClickable(false);
                break;
        }
    }
}
