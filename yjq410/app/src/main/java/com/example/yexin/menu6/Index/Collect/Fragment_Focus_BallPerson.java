package com.example.yexin.menu6.Index.Collect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.yexin.menu6.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 23646 on 2019/10/31.
 */

public class Fragment_Focus_BallPerson extends Fragment{
    private List<Focus_Item> mData;
    private Context mContext;
    private ListView lv_focus_recommend;
    private TextView tv_focus_ballperson_number;
    public static Fragment newInstance() {
        Fragment_Focus_BallPerson fragment = new Fragment_Focus_BallPerson();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_focus_ballperson, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_focus_recommend = (ListView)rootView.findViewById(R.id.lv_focus_recommend);
        tv_focus_ballperson_number = (TextView)rootView.findViewById(R.id.tv_focus_ballperson_number);
        mData = new LinkedList<Focus_Item>();
        mContext = getContext();
        initial_Recommend_Data();
        lv_focus_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return rootView;
    }

    //加载推荐人员数据
    public void initial_Recommend_Data(){

        lv_focus_recommend.setAdapter(new Focus_Item_Adapter((LinkedList<Focus_Item>) mData,mContext));
    }
}
