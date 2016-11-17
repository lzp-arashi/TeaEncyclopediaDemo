package com.example.administrator.teaencyclopediademo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.util.BitmapUtils;

/**
 * Created by Administrator on 2016/11/11.
 */
public class HeadPagerAdapter extends BaseListViewAdapter<PageEntity.PageDetial>{

    public HeadPagerAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null) {
            view=getInflater().inflate(R.layout.item_page,viewGroup,false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        PageEntity.PageDetial item=getItem(i);
        viewHolder.title.setText(item.getTitle());
        viewHolder.description.setText(""+item.getDescription());
        String detial=item.getSource()+" "+item.getNickname()+" "+item.getCreate_time();
        viewHolder.source.setText(detial);
        String imgUrl=item.getWap_thumb();
        BitmapUtils.getInstene().loadImg(imgUrl,viewHolder.wap_thumb);
        return view;
    }
    class ViewHolder{
        private ImageView wap_thumb;
        private TextView title,source,description;
        public ViewHolder(View convertView){
            wap_thumb = (ImageView) convertView.findViewById(R.id.item_page_wap_thumbId);
            title = (TextView) convertView.findViewById(R.id.item_page_titleId);
            source = (TextView) convertView.findViewById(R.id.item_page_sourceId);
            description = (TextView) convertView.findViewById(R.id.item_page_descriptionId);
        }
    }

}
