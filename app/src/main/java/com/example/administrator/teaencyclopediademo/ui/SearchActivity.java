package com.example.administrator.teaencyclopediademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.adapter.HeadPagerAdapter;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.url.AppInterface;
import com.example.administrator.teaencyclopediademo.util.HttpUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private PullToRefreshListView refreshListView;
    private HeadPagerAdapter adapter;
    private String search = "茶";
    private int page = 1;
    private TextView textView;
    private List<PageEntity.PageDetial> detial = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search=getIntent().getStringExtra("search");
        refreshListView = ((PullToRefreshListView) findViewById(R.id.search_listView));
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //adapter.clear();
                detial.clear();
                page=1;
                loadDate(page,search);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                loadDate(page,search);
            }
        });
        textView = ((TextView) findViewById(R.id.search_keyword));
        textView.setText(search);
        adapter = new HeadPagerAdapter(this);
        refreshListView.setAdapter(adapter);
        loadDate(1,search);
    }

    private void loadDate(int page,String search) {
        HttpUtils.getInstance().sendRequest(String.format(AppInterface.SEARCH_NEWS, page, search),
                new HttpUtils.Listener() {
                    @Override
                    public void onresponse(String result) {
                        Gson gson = new Gson();
                        PageEntity pageEntity = gson.fromJson(result,PageEntity.class);
                        detial.addAll(pageEntity.getData());
                        adapter.addAll(pageEntity.getData());
                        refreshListView.onRefreshComplete();
                    }
                });
    }

    public void homebtn(View view){
        startActivity( new Intent(SearchActivity.this,MainActivity.class));
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
            Toast.makeText(SearchActivity.this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            backStep++;
        }
    }
}
