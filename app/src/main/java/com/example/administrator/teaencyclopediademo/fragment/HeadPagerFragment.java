package com.example.administrator.teaencyclopediademo.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.adapter.HeadPagerAdapter;
import com.example.administrator.teaencyclopediademo.adapter.HeadViewAdapter;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.bean.SlideEntity;
import com.example.administrator.teaencyclopediademo.ui.DetialActivity;
import com.example.administrator.teaencyclopediademo.ui.MainActivity;
import com.example.administrator.teaencyclopediademo.url.AppInterface;
import com.example.administrator.teaencyclopediademo.util.BitmapUtils;
import com.example.administrator.teaencyclopediademo.util.HttpUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class HeadPagerFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private PullToRefreshListView pullListView;
    private HeadPagerAdapter adapter;
    private List<PageEntity.PageDetial> data=new ArrayList<>();
    private ViewPager viewPager;
    private List<SlideEntity.SlideShow> slideShows=new ArrayList<>();
    private HeadViewAdapter headerViewAdapter;
    private View headerView;
    private LinearLayout linearLayout;
    private List<ImageView> imageViews=new ArrayList<>();
    private int page = 1;

    public HeadPagerFragment() {

    }

    public static HeadPagerFragment newInstance() {
        HeadPagerFragment fragment = new HeadPagerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        pullListView = ((PullToRefreshListView) rootView.findViewById(R.id.fragment_page_listViewId));
        pullListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //adapter.clear();
                data.clear();
                page = 1;
                loadData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                loadData(page);
            }
        });
        final ListView listView=pullListView.getRefreshableView();
        headerView = inflater.inflate(R.layout.head_page, listView, false);
        //初始化头布局
        initHeaderView();
        //加载头布局数据
        loadHeadViewData();
        listView.addHeaderView(headerView);
        //实例化适配器
        adapter = new HeadPagerAdapter(getContext());
        //设置适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, final long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //设置
                builder.setTitle("提示");
                builder.setMessage("您是否要删除第"+(i-1)+"行");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Animation translate = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF,0,
                                Animation.RELATIVE_TO_SELF,-1,//移动自身的宽度
                                Animation.RELATIVE_TO_SELF,0,
                                Animation.RELATIVE_TO_SELF,0);
                        translate.setDuration(2000);
                        view.startAnimation(translate);

                        //动画的监听
                        translate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {//启动
                                Log.d("1608",data.toString());
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {//结束
                                //将数据删除，并且更新适配器
                                adapter.remove(i-2);
                                //adapter.remove(i-2);
                                Log.d("1608",data.toString());
                                //刷新适配器
                                //adapter.notifyDataSetChanged();
                                Log.d("1608",data.toString());
                                int count = listView.getChildCount();

                                AnimationSet set = new AnimationSet(true);
                                //渐变
                                AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
                                alphaAnimation.setDuration(1000);

                                //缩放
                                ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f);
                                scaleAnimation.setDuration(1000);
                                set.addAnimation(alphaAnimation);
                                set.addAnimation(scaleAnimation);
                                int currentTop = view.getTop();
                                for (int i1 = 0; i1 < count; i1++) {
                                    View itemView = listView.getChildAt(i1);

                                    if (itemView.getTop()>=currentTop)
                                        itemView.startAnimation(set);
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {//启动

                            }
                        });
                    }
                });
                //目的
                builder.create().show();
                return true;
            }
        });
        return rootView;
    }

    private void loadHeadViewData() {
        HttpUtils.getInstance().sendRequest(AppInterface.HEADER_VIEW_NEWS, new HttpUtils.Listener() {
            @Override
            public void onresponse(String result) {
                Gson gson=new Gson();
                SlideEntity slideEntrity=gson.fromJson(result,SlideEntity.class);
                slideShows.addAll(slideEntrity.getData());
                //设置头布局中的数据
                init();
            }
        });
    }

    private void init() {
        for (int i = 0; i <slideShows.size() ; i++) {
            ImageView img=new ImageView(getContext());
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            BitmapUtils.getInstene().loadImg(slideShows.get(i).getImage(),img);
            imageViews.add(img);
        }
        headerViewAdapter.notifyDataSetChanged();

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((MainActivity)getContext()).getViewPager().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i <linearLayout.getChildCount() ; i++) {
                    ImageView view = (ImageView) linearLayout.getChildAt(i);
                    if(i==position){
                        view.setSelected(true);
                    }else{
                        view.setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initHeaderView() {
        viewPager = ((ViewPager) headerView.findViewById(R.id.header_viewPagerId));
        linearLayout = ((LinearLayout) headerView.findViewById(R.id.header_line2));
        linearLayout.getChildAt(0).setSelected(true);
        headerViewAdapter = new HeadViewAdapter(imageViews);
        viewPager.setAdapter(headerViewAdapter);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        loadData(page);
        super.onActivityCreated(savedInstanceState);
    }

    private void loadData(int page) {
        HttpUtils.getInstance().sendRequest(String.format(AppInterface.HEAD_NEWS,page), new HttpUtils.Listener() {
            @Override
            public void onresponse(String result) {
                Gson gson=new Gson();
                PageEntity news=gson.fromJson(result,PageEntity.class);
                data.addAll(news.getData());
                adapter.addAll(news.getData());
                pullListView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PageEntity.PageDetial detial = data.get(i-2);
        //Log.d("1608",detial.getTitle());
        Intent intent=new Intent(getActivity(), DetialActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detial",detial);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
