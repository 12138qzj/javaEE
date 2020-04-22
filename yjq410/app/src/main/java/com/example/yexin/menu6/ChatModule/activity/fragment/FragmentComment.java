package com.example.yexin.menu6.ChatModule.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yexin.menu6.ChatModule.Data.CommentChatData;
import com.example.yexin.menu6.ChatModule.Data.PrivateChatData;
import com.example.yexin.menu6.ChatModule.Support.ItemClickSupport;
import com.example.yexin.menu6.ChatModule.activity.adapter.CommentChatAdapter;
import com.example.yexin.menu6.ChatModule.activity.adapter.PrivateChatAdapter;
import com.example.yexin.menu6.R;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/3/29.
 */

public class FragmentComment extends Fragment {
    private RecyclerView recyclerView;
    private CommentChatAdapter commentChatAdapter;
    private LinkedList<CommentChatData> chatDatas;
    private TextView null_con;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.comment_fragment, null);
    recyclerView=(RecyclerView)view.findViewById(R.id.conv_commont_view);
    null_con=(TextView)view.findViewById(R.id.null_conversation);


    chatDatas=new LinkedList<>();
        chatDatas.add(new CommentChatData("环境好嘛","用户甲","14:11"));
        chatDatas.add(new CommentChatData("在哪里","用户乙","15:11"));
        chatDatas.add(new CommentChatData("光线怎么样","用户丙","11:11"));


        commentChatAdapter=new CommentChatAdapter(getContext(),chatDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commentChatAdapter);

        if(chatDatas.size()==0){
        null_con.setVisibility(View.VISIBLE);
    }else{
        null_con.setVisibility(View.GONE);
    }


    //点击监听
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
        private Intent intent=new Intent();
        @Override
        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
            Log.e("列表点击","你点击了"+chatDatas.get(position).getPeople());
        }
    });
    //长按监听
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
            Log.e("列表长按","你长按了"+chatDatas.get(position).getPeople());
            return false;
        }
    });

        return view;
}
}
