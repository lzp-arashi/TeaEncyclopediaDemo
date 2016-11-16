package com.example.administrator.teaencyclopediademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.bean.WebEntity;
import com.example.administrator.teaencyclopediademo.url.AppInterface;
import com.example.administrator.teaencyclopediademo.util.HttpUtils;
import com.example.administrator.teaencyclopediademo.util.MyDateBaseUtils;
import com.example.administrator.teaencyclopediademo.util.MyDateUtils;
import com.google.gson.Gson;

public class DetialActivity extends AppCompatActivity{
    private TextView title;
    private TextView time;
    private PageEntity.PageDetial detial;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_web);
        detial = (PageEntity.PageDetial) getIntent().getSerializableExtra("detial");

        MyDateUtils.getInerence(this).insertData(detial);

        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.web_title);
        time = (TextView) findViewById(R.id.web_time);
        webView = (WebView) findViewById(R.id.webView_Id);
        //Log.d("1608",detial.getTitle());
        title.setText(detial.getTitle());
        time.setText("创建时间:"+detial.getCreate_time()+"  来源："+detial.getSource());
        int id=Integer.parseInt(String.valueOf(detial.getId()));
        HttpUtils.getInstance().sendRequest(String.format(AppInterface.NEWS_DETIAL,id), new HttpUtils.Listener() {
            @Override
            public void onresponse(String result) {
                Gson gson=new Gson();
                WebEntity.WebItem item=gson.fromJson(result,WebEntity.class).getData();
                //Log.d("1608",item.getWap_content());
                webView.loadDataWithBaseURL(null,item.getWap_content(),"text/html","UTF-8",null);
            }
        });
    }

    public void fanhuibtn(View view){
        startActivity( new Intent(DetialActivity.this,MainActivity.class));
        finish();
    }

    public void shoucangbtn(View view) {
        MyDateBaseUtils.getInerence(this).insertData(detial);
        Toast.makeText(DetialActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
    }

}
