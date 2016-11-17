package com.example.administrator.teaencyclopediademo.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.adapter.HeadPagerAdapter;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.ui.DetialActivity;
import com.example.administrator.teaencyclopediademo.url.AppInterface;
import com.example.administrator.teaencyclopediademo.util.HttpUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherPageFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView refreshListView;
    private HeadPagerAdapter adapter;
    private int type = 16;
    private int page = 1;
    private List<PageEntity.PageDetial> detials=new ArrayList<>();
    public OtherPageFragment() {
        // Required empty public constructor
    }

    public static OtherPageFragment newInstance(int type) {
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        OtherPageFragment fragment = new OtherPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other_page, container, false);
        refreshListView = ((PullToRefreshListView) rootView.findViewById(R.id.other_page_listViewId));
        //实例化适配器
        adapter = new HeadPagerAdapter(getContext());
        //设置适配器
        refreshListView.setAdapter(adapter);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        detials.clear();
                        adapter.clear();
                        page = 1;
                        loadData(type,page);
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        page++;
                        loadData(type,page);
                    }
                }
        );
        refreshListView.setOnItemClickListener(this);
        final ListView listView=refreshListView.getRefreshableView();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,final View view,final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("您是否要删除第"+i+"行");
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
                                Log.d("1608",detials.toString());
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                adapter.remove(i-1);
                                Log.d("1608",detials.toString());
                                //adapter.notifyDataSetChanged();
                                Log.d("1608",detials.toString());
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
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        type=getArguments().getInt("type");
        loadData(type,1);
        super.onActivityCreated(savedInstanceState);
    }

    private void loadData(int type, int i) {
        HttpUtils.getInstance().sendRequest(String.format(AppInterface.OTHER_NEWS, type, i), new HttpUtils.Listener() {
            @Override
            public void onresponse(String result) {
                Gson gson=new Gson();
                PageEntity newsEntrity=gson.fromJson(result,PageEntity.class);
                detials.addAll(newsEntrity.getData());
                adapter.addAll(newsEntrity.getData());
                refreshListView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(), DetialActivity.class);
        PageEntity.PageDetial detial=detials.get(i-1);
        Bundle bundle=new Bundle();
        bundle.putSerializable("detial", detial);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
