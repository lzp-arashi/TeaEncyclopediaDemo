package com.example.administrator.teaencyclopediademo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.teaencyclopediademo.R;
import com.example.administrator.teaencyclopediademo.bean.PageEntity;
import com.example.administrator.teaencyclopediademo.util.BitmapUtils;
import com.example.administrator.teaencyclopediademo.util.cache.impl.MyLruCache;

/**
 * Created by Administrator on 2016/11/11.
 */
public class HeadPagerAdapter extends BaseListViewAdapter<PageEntity.PageDetial>{

    private MyLruCache myLruCache;

    public HeadPagerAdapter(Context context) {
        super(context);
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        //就是一个链表，相当于List
        //分配了内存了空间的八分之一
        myLruCache = new MyLruCache(maxSize);
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

    /*private Bitmap getCache(String img) {
        //从缓存中获取数据
        //从内存中获取数据---->myLruCache---->第一级
        img = img.replaceAll("/","");
        Bitmap bitmap = myLruCache.get(img);
        if (bitmap != null) {
            Log.d("flag", "----------------->getCache: 从内存LruCache中获取数据");
            return bitmap;
        }else {//内存中没有该图片的数据
            //从磁盘获取这个数据----->第二级
            String root = mContext.getExternalCacheDir().getAbsolutePath();
            String fileName = root+ File.separator+img;
            byte[] bytes = SdCardUtils.getbyteFromFile(fileName);
            if (bytes != null) {
                Bitmap bitmapSd = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                //将磁盘中数据保存到内存中
                myLruCache.put(img,bitmapSd);
                Log.d("flag", "----------------->getCache: 从磁盘获取数据");
                //从Sd卡获取了该图片
                return bitmapSd;
            }
        }
        return null;
    }*/

}
