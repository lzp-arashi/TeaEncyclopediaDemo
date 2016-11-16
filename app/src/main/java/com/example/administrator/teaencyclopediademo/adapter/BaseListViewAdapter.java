package com.example.administrator.teaencyclopediademo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListViewAdapter<T> extends BaseAdapter {
    private List<T> datas;
    private LayoutInflater inflater;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public BaseListViewAdapter(Context context) {
        this.datas = new ArrayList<T>();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 添加一个集合
     * @param dd
     */
    public void addAll(List<T> dd)
    {
        datas.addAll(dd);
        notifyDataSetChanged();
    }

    public void clear()
    {
        datas.clear();
        notifyDataSetChanged();
    }
}
