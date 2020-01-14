package com.example.customview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.views.light.LightTextView;

public class LightTextActivity extends AppCompatActivity implements View.OnClickListener {

    private LightTextView mLightTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_light_text);
        mLightTextView = findViewById(R.id.lightTextView);

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mLightTextView.start();
                break;
            case R.id.btn_stop:
                mLightTextView.stop();
                break;
        }
    }
}
