package com.geektech.weatherapplication.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

abstract public class BaseActivity extends AppCompatActivity {

    public abstract int getViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        ButterKnife.bind(this);
    }

     protected void toast(String message){
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
     }
}
