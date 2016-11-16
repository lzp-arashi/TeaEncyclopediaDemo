package com.example.administrator.teaencyclopediademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.teaencyclopediademo.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity{
    private TextView mTextView;
    private ImageView view;
    private Timer timer;
    private int time=3;
    private final int MSG_WHAT = 10;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_WHAT:
                    mTextView.setText(""+time);
                    time--;
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mTextView = (TextView) findViewById(R.id.activity_welcome_timeId);
        view = (ImageView) findViewById(R.id.activity_welcome_imgId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    WelcomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity( new Intent(WelcomeActivity.this,Welcome2Activity.class));
                            //overridePendingTransition();
                            finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = MSG_WHAT;
                handler.sendEmptyMessage(msg.what);
            }
        },0,1000);
    }
}
