package com.example.administrator.teaencyclopediademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.teaencyclopediademo.R;

public class VersionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versions);
    }
    public void homebtn(View view){
        startActivity( new Intent(VersionsActivity.this,MainActivity.class));
        finish();
    }
    private int backStep = 0;
    @Override
    public void onBackPressed() {
        if(backStep == 0 ){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        backStep = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if(backStep == 1 ){
            super.onBackPressed();
        }else {
            Toast.makeText(VersionsActivity.this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            backStep++;
        }
    }
}
