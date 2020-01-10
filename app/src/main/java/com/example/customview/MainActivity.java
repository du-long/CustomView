package com.example.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.views.DropMenuActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        Class activity = null;
        switch (v.getId()) {
            case R.id.btn_drop_menu:

                activity = DropMenuActivity.class;
                break;
            default:
        }
        startActivity(new Intent(this, activity));
    }
}
