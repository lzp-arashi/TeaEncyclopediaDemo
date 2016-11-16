package com.example.administrator.teaencyclopediademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.adapter.WelcomePageAdapter;

import java.util.ArrayList;
import java.util.List;

public class Welcome2Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager welcomeViewPager;
    private List<ImageView> imgList;
    private Button mButton;
    private int[] imgIds = new int[]{R.drawable.slide1,R.drawable.slide2,R.drawable.slide3,};
    // 点集合
    private List<ImageView> pointList;
    private int[] pointIds = new int[]{R.id.point01_iv, R.id.point02_iv, R.id.point03_iv};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);
        welcomeViewPager = (ViewPager) findViewById(R.id.welcomeView_pager);
        mButton = (Button) findViewById(R.id.activity_welcome2_button);
        // 创建视图
        imgList = new ArrayList<>();
        pointList = new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgIds[i]);
            imgList.add(imageView);
            // 初始化点
            ImageView pointImageView = (ImageView) findViewById(pointIds[i]);
            pointList.add(pointImageView);

        }
        // 默认第一个点为选中状态
        pointList.get(0).setSelected(true);
        WelcomePageAdapter welcomePagerAdapter = new WelcomePageAdapter(imgList);
        welcomeViewPager.setAdapter(welcomePagerAdapter);
        welcomeViewPager.addOnPageChangeListener(this);
    }
    public void tiyanbtn(View view){
        startActivity( new Intent(Welcome2Activity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < pointList.size(); i++) {
            pointList.get(i).setSelected(i == position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        welcomeViewPager.removeOnPageChangeListener(this);
    }
}
