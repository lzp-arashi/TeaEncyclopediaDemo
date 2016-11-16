package com.example.administrator.teaencyclopediademo.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.adapter.HeadPagerAdapter;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.util.MyDateBaseUtils;
import com.example.administrator.teaencyclopediademo.util.MyMessagePage;
import com.example.administrator.teaencyclopediademo.util.MySQLHelper;

import java.util.List;

public class CollectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private HeadPagerAdapter adapter;
    private List<PageEntity.PageDetial> mCurrData;
    private SQLiteDatabase sqLiteDatabase;

    //private List<PageEntity.PageDetial> detials=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        listView = (ListView) findViewById(R.id.collection_listView);
        adapter = new HeadPagerAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        loadData();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectActivity.this);
                builder.setTitle("提示");
                builder.setMessage("您是否要删除第"+(i+1)+"行");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int which) {
                        TranslateAnimation translate = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF,0,
                                Animation.RELATIVE_TO_SELF,-1,
                                Animation.RELATIVE_TO_SELF,0,
                                Animation.RELATIVE_TO_SELF,0);
                        translate.setDuration(1000);
                        view.startAnimation(translate);
                        translate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mCurrData = MyDateBaseUtils.getShuju();
                                String id = mCurrData.get(i).getId();
                                Log.d("1608",id);
                                mCurrData.remove(i);
                                adapter.notifyDataSetChanged();
                                sqLiteDatabase.delete(MySQLHelper.TABLE_NAME, "id = ?", new String[]{id});
                                int count = listView.getChildCount();
                                AnimationSet set = new AnimationSet(true);
                                AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
                                alphaAnimation.setDuration(1000);
                                ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,
                                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                                scaleAnimation.setDuration(1000);
                                set.addAnimation(alphaAnimation);
                                set.addAnimation(scaleAnimation);

                                int currentTop = view.getTop();
                                for (int i = 0; i < count; i++) {
                                    View itemView = listView.getChildAt(i);

                                    if(itemView.getTop()>=currentTop)
                                        itemView.startAnimation(set);

                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    private void loadData() {
        MyDateBaseUtils.getInerence(this).getDetial(new MyDateBaseUtils.ListenerDB() {
            @Override
            public void onresponse(List<PageEntity.PageDetial> detials) {
                adapter.addAll(detials);
            }
        });
    }

    public void homebtn(View view){
        startActivity( new Intent(CollectActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //view.get
        mCurrData = MyDateBaseUtils.getShuju();

        //PageEntity.PageDetial detial= mCurrData.get(i);
        Log.d("1608", mCurrData.get(i).getTitle());
        Log.d("1608", mCurrData.get(i).getId());
        Intent intent=new Intent(this, MyMessagePage.class);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("detial",detial);
        //intent.putExtras(bundle);
        intent.putExtra("id", mCurrData.get(i).getId());
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
            Toast.makeText(CollectActivity.this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            backStep++;
        }
    }
}
