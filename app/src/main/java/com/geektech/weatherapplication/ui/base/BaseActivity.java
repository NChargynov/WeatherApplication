package com.geektech.weatherapplication.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

 abstract public class BaseActivity extends AppCompatActivity {

//     protected abstract int getViewLayout();

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
         super.onCreate(savedInstanceState, persistentState);
//         setContentView(getViewLayout());
     }

     protected void toast(String message){
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
     }
}
