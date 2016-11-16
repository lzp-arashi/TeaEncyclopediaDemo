package com.example.administrator.teaencyclopediademo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.teaencyclopediademo.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class MainActivityAdapter extends FragmentPagerAdapter{
    private List<BaseFragment> fragments;
    public MainActivityAdapter(android.support.v4.app.FragmentManager fm,List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    public Fragment getItem(int position){
        return fragments.get(position);
    }
    public int getCount(){
        return fragments.size();
    }
}
