package com.example.administrator.teaencyclopediademo.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.bean.WebEntity;
import com.example.administrator.teaencyclopediademo.ui.MainActivity;
import com.google.gson.Gson;

public class MyMessage extends AppCompatActivity{
    private TextView title;
    private TextView time;
    private PageEntity.PageDetial detial;
    private WebView webView;
    private WebEntity data;
    private Handler handler = new Handler();
    public static final String NEWS_DETIAL="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=%s";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_web);
        title = (TextView) findViewById(R.id.web_title);
        time = (TextView) findViewById(R.id.web_time);
        webView = (WebView) findViewById(R.id.webView_Id);
        Intent intent =getIntent();
        String id = intent.getStringExtra("id");
        final String currURL =String.format(NEWS_DETIAL,id);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String json = HttpUtils.getJsonStringFromInternet(currURL);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        data = new Gson().fromJson(json,WebEntity.class);
                        initView(data);
                    }
                });
            }
        }).start();
       // detial = (PageEntity.PageDetial) getIntent().getSerializableExtra("detial");

    }

    private void initView(WebEntity data) {

        title.setText(data.getData().getTitle());
        time.setText("创建时间:"+data.getData().getCreateTime()+"  来源："+data.getData().getSource());
        webView.loadDataWithBaseURL(null,data.getData().getWap_content(),"text/html","UTF-8",null);
    }

    public void fanhuibtn(View view){
        startActivity( new Intent(MyMessage.this,MainActivity.class));
        finish();
    }

    public void shoucangbtn(View view) {
        MyDateBaseUtils.getInerence(this).insertData(detial);
        Toast.makeText(MyMessage.this,"收藏成功",Toast.LENGTH_SHORT).show();
    }
}
