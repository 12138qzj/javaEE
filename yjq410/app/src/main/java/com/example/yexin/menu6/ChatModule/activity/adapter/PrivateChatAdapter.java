package com.example.yexin.menu6.ChatModule.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexin.menu6.ChatModule.Data.PrivateChatData;
import com.example.yexin.menu6.Person_Information.ModifyInfomation.Nickname;
import com.example.yexin.menu6.R;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/3/29.
 */

public class PrivateChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private LayoutInflater mLayoutInflater;
    private Context mContext;  //上下文
    private LinkedList<PrivateChatData> mDatas;

    /**
     *
     * @param context
     * @param datas
     */
    public PrivateChatAdapter(Context context, LinkedList<PrivateChatData> datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    /**
     * 在新的数据来的时候，在第一个位置添加数据并更新
     * @param msg
     */
    public LinkedList<PrivateChatData> addItem(PrivateChatData msg) {
        mDatas.addFirst(msg);
        notifyItemInserted(0);
        notifyDataSetChanged();
        return  mDatas;
    }

    public void ChangeDate(LinkedList<PrivateChatData> msg){
        mDatas=msg;
        notifyDataSetChanged();
    }

    public void ChangeItem(int a,PrivateChatData msg){
        mDatas.remove(a);
        notifyItemRemoved(a);
        mDatas.addFirst(msg);
        notifyItemInserted(0);
        notifyDataSetChanged();
    }

    public LinkedList<PrivateChatData> deleteItem(int position){
        mDatas.remove(position);
        notifyDataSetChanged();
        return mDatas;
    }

    /**
     * 绑定视图的XML，界面绑定
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.receipt_no_read, parent, false);
        return new PrivateChat(view);
    }

    /**
     * 控件绑定设置变量的内容，内容赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PrivateChatData msg_ = mDatas.get(position);
        String account=msg_.getAccount();
//        int header=msg_.getHeader();
        String time=msg_.getTime();
        String nickname=msg_.getNickName();
        int header=msg_.getIconHeader();
        ((PrivateChat) holder).mChat.setText(account);
        ((PrivateChat) holder).mTime.setText(time);
        ((PrivateChat) holder).NickName.setText(nickname);
        ((PrivateChat) holder).mHeader.setImageResource(header);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class PrivateChat extends RecyclerView.ViewHolder{
        TextView mChat;
        TextView mTime;
        TextView NickName;
        ImageView mHeader;

        PrivateChat(View view) {
            super(view);
            mChat=(TextView)view.findViewById(R.id.tv_noRead);
            mTime=(TextView)view.findViewById(R.id.tv_count);
            mHeader=(ImageView)view.findViewById(R.id.iv_noRead);
            NickName=(TextView)view.findViewById(R.id.tv_nickname);
        }
    }
}
