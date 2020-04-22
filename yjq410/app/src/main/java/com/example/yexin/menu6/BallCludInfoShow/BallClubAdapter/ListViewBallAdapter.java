package com.example.yexin.menu6.BallCludInfoShow.BallClubAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexin.menu6.BallCludInfoShow.BallClubData.ListViewBallData;
import com.example.yexin.menu6.R;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/4/20.
 */

public class ListViewBallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private LinkedList<ListViewBallData> mDatas;

    /**
     *
     * @param context
     * @param datas
     */
    public ListViewBallAdapter(Context context, LinkedList<ListViewBallData> datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    public void ChangeDate(LinkedList<ListViewBallData> msg){
        mDatas=msg;
        notifyDataSetChanged();
    }

    /**
     * 绑定视图的XML，界面绑定
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.ballselect, parent, false);
        return new PrivateChat(view);
    }

    /**
     * 控件绑定设置变量的内容，内容赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListViewBallData msg_ = mDatas.get(position);
        boolean ballchoose=msg_.isBallChoose();
        int ballicon=msg_.getBallIcon();
        String ballname=msg_.getBallName();
        String ballprice=msg_.getBallPrice();
        ((PrivateChat) holder).BallIcon.setImageResource(ballicon);
        ((PrivateChat) holder).BallName.setText(ballname);
        ((PrivateChat) holder).BallPrice.setText(ballprice);
        if(ballchoose) ((PrivateChat) holder).BallChoose.setImageResource(R.drawable.showxz);
        else ((PrivateChat) holder).BallChoose.setImageResource(R.drawable.showwxz);
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
        ImageView BallIcon;
        TextView BallName;
        TextView BallPrice;
        ImageView BallChoose;

        PrivateChat(View view) {
            super(view);
            BallIcon=(ImageView) view.findViewById(R.id.BallIcon);
            BallName=(TextView)view.findViewById(R.id.BallName);
            BallPrice=(TextView) view.findViewById(R.id.BallPrice);
            BallChoose=(ImageView) view.findViewById(R.id.BallChoose);
        }
    }
}
