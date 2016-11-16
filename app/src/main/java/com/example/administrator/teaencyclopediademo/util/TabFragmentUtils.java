package com.example.administrator.teaencyclopediademo.util;

import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.teaencyclopediademo.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/10/5.
 */
public class TabFragmentUtils implements RadioGroup.OnCheckedChangeListener {
    private List<BaseFragment> fragments;
    private int containerLayout;
    private FragmentManager fragmentManager;

    public TabFragmentUtils(RadioGroup radioGroup, List<BaseFragment> fragments, int containerLayout, FragmentManager fragmentManager) {
        this.fragments = fragments;
        this.containerLayout = containerLayout;
        this.fragmentManager = fragmentManager;
        //设置radiogroup的选中监听
        radioGroup.setOnCheckedChangeListener(this);
        //默认选中第0个
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton view = (RadioButton) group.getChildAt(i);
            if(view.getId() == checkedId)
            {
                //显示响应的页面，隐藏其他页面
                showFragmentByIndex(i);
            }
        }
    }

    private void showFragmentByIndex(int index) {
        for (int i = 0; i < fragments.size(); i++) {
            if(i == index)
            {
                if (fragments.get(i).isAdded()) {
                    fragmentManager.beginTransaction().show(fragments.get(i)).commit();
                }
                else
                {
                    fragmentManager.beginTransaction().add(containerLayout,fragments.get(i)).commit();
                }
            }
            else
            {
                if (fragments.get(i).isAdded()) {
                    fragmentManager.beginTransaction().hide(fragments.get(i)).commit();
                }
            }
        }
    }
}
