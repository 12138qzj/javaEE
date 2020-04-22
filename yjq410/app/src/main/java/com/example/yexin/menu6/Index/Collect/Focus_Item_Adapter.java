package com.example.yexin.menu6.Index.Collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yexin.menu6.R;

import java.util.LinkedList;

/**
 * Created by 23646 on 2020/3/26.
 */

public class Focus_Item_Adapter extends BaseAdapter {
    private LinkedList<Focus_Item> mData;
    private Context mContext;

    public Focus_Item_Adapter(LinkedList<Focus_Item> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.focus_item,parent,false);
            holder = new ViewHolder();
            holder.iv_focus = (ImageView) convertView.findViewById(R.id.iv_focus);
            holder.tv_focus_name = (TextView) convertView.findViewById(R.id.tv_focus_name);
            holder.tv_focus_description = (TextView) convertView.findViewById(R.id.tv_focus_description);
            convertView.setTag(holder);   //将Holder存储到convertView中
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_focus.setBackgroundResource(mData.get(position).getIv_focus());
        holder.tv_focus_name.setText(mData.get(position).getTv_focus_name());
        holder.tv_focus_description.setText(mData.get(position).getTv_focus_description());
        return convertView;
    }

    static class ViewHolder{
        ImageView iv_focus;
        TextView tv_focus_name;
        TextView tv_focus_description;
    }
}
