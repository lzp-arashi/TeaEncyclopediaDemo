package com.example.administrator.teaencyclopediademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.adapter.HeadPagerAdapter;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.util.MyDateUtils;
import com.example.administrator.teaencyclopediademo.util.MyMessage;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private HeadPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView) findViewById(R.id.history_listView);
        adapter = new HeadPagerAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        loadData();
    }

    private void loadData() {
        MyDateUtils.getInerence(this).getDetial(new MyDateUtils.ListenerDB() {
            @Override
            public void onresponse(List<PageEntity.PageDetial> detials) {
                adapter.addAll(detials);
            }
        });
    }

    public void homebtn(View view){
        startActivity( new Intent(HistoryActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        List<PageEntity.PageDetial> currData = MyDateUtils.getShuju2();

        PageEntity.PageDetial detial=currData.get(i);
        Intent intent=new Intent(this, MyMessage.class);
        intent.putExtra("id",currData.get(i).getId());
        startActivity(intent);
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
            Toast.makeText(HistoryActivity.this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            backStep++;
        }
    }
}
