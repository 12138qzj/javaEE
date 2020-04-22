package com.example.yexin.menu6.BallCludInfoShow.BallClubAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.example.yexin.menu6.BallCludInfoShow.BallClubData.ListViewPlaceData;
import com.example.yexin.menu6.R;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/4/20.
 */

public class ListViewPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private LinkedList<ListViewPlaceData> mDatas;

    final static int KEYOK=121213;
    final static int KEYNO=121214;

    /**
     *
     * @param context
     * @param datas
     */
    public ListViewPlaceAdapter(Context context, LinkedList<ListViewPlaceData> datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    public void ChangeDate(LinkedList<ListViewPlaceData> msg){
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
        if(viewType==ListViewPlaceAdapter.KEYOK){
            View view = mLayoutInflater.inflate(R.layout.placeselect, parent, false);
            return new ListViewPlaceAdapter.selectPlace(view);
        }else{
            View view = mLayoutInflater.inflate(R.layout.placeselect, parent, false);
            return new ListViewPlaceAdapter.selectPlace(view);
        }
    }

    /**
     * 控件绑定设置变量的内容，内容赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListViewPlaceData msg_ = mDatas.get(position);
        String name=msg_.getPlaceName();
        ((selectPlace) holder).textView.setText(name);
        //((selectPlace) holder).checkBox.setTag(name);
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getIsHave();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class selectPlace extends RecyclerView.ViewHolder{

        TextView textView;

        public selectPlace(View view) {
            super(view);
            textView=(TextView) view.findViewById(R.id.textviewPlace);
        }
    }
}
