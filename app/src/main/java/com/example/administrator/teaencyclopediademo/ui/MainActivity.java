package com.example.administrator.teaencyclopediademo.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.adapter.MainActivityAdapter;
import com.example.administrator.teaencyclopediademo.animation.AccordionTransformer;
import com.example.administrator.teaencyclopediademo.fragment.BaseFragment;
import com.example.administrator.teaencyclopediademo.fragment.HeadPagerFragment;
import com.example.administrator.teaencyclopediademo.fragment.OtherPageFragment;
import com.example.administrator.teaencyclopediademo.util.BitmapUtils;
import com.example.administrator.teaencyclopediademo.util.cache.impl.MemoryImg;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private LinearLayout title_Tab;
    private DrawerLayout drawerLayout_layout;
    private LinearLayout rightLayout;
    private EditText searchEditorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BitmapUtils.getInstene().setImageCache(new MemoryImg(10<<20));
        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main_relativeLayoutId,new HeadPagerFragment())
                .commit();*/
        viewPager = ((ViewPager) findViewById(R.id.activity_main_viewPagerId));
        linearLayout = ((LinearLayout) findViewById(R.id.activity_main_title_line));
        title_Tab = ((LinearLayout) findViewById(R.id.activity_main_title_text));
        drawerLayout_layout = ((DrawerLayout) findViewById(R.id.activity_main_drawerLayout_layout));
        rightLayout = ((LinearLayout) findViewById(R.id.right_drawLayout_menu));
        searchEditorText = ((EditText) findViewById(R.id.activity_main_searchEditText));
        initViewPager();
        initTab();
    }

    private void initTab() {
        for (int i = 0; i <title_Tab.getChildCount()-1 ; i++) {
            TextView textView = (TextView) title_Tab.getChildAt(i);
            final int position=i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(position);
                }
            });
        }
    }

    //初始化viewpager和adapter
    private void initViewPager() {
        ArrayList<BaseFragment> baseFragments = new ArrayList<>();
        baseFragments.add(HeadPagerFragment.newInstance());
        baseFragments.add(OtherPageFragment.newInstance(16));
        baseFragments.add(OtherPageFragment.newInstance(52));
        baseFragments.add(OtherPageFragment.newInstance(54));
        baseFragments.add(OtherPageFragment.newInstance(53));
        MainActivityAdapter adapter=new MainActivityAdapter(getSupportFragmentManager(),baseFragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        selecTab(0);
        viewPager.setPageTransformer(true,new AccordionTransformer());
    }
    //呼出侧滑菜单
    public void moreSlidingMneu(View view) {
        switch (view.getId()){
            case R.id.activity_main_open_menu:
                drawerLayout_layout.openDrawer(rightLayout);
                Toast.makeText(MainActivity.this,"抽屉被打开",Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_main_close_menu:
                drawerLayout_layout.closeDrawer(rightLayout);
                Toast.makeText(MainActivity.this,"抽屉被关闭",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selecTab(position);
    }

    private void selecTab(int position) {
        for (int i = 0; i <linearLayout.getChildCount()-1 ; i++) {
            View view = linearLayout.getChildAt(i);
            if(position==i){
                view.setBackgroundColor(Color.parseColor("#00AA00"));
            }else{
                view.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
            Toast.makeText(MainActivity.this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            backStep++;
        }
    }

    public void mycollectbtn(View view){
        startActivity( new Intent(MainActivity.this,CollectActivity.class));
        finish();
    }

    public void versionsbtn(View view){
        startActivity( new Intent(MainActivity.this,VersionsActivity.class));
        finish();
    }

    public void feedbackbtn(View view){
        startActivity( new Intent(MainActivity.this,FeedbackActivity.class));
        finish();
    }

    public void searchbtn(View view){
        String keyWord =searchEditorText.getText().toString();
        Intent intent = new Intent(MainActivity.this,SearchActivity.class);
        intent.putExtra("search",keyWord);
        startActivity(intent);
        finish();
    }

    public void myhistory(View view) {
        startActivity(new Intent(MainActivity.this,HistoryActivity.class));
        finish();
    }

    public ViewParent getViewPager() {
        return viewPager;
    }
}
