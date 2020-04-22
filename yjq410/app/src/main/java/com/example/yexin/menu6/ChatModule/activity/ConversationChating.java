package com.example.yexin.menu6.ChatModule.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexin.menu6.ChatModule.Data.Chating;
import com.example.yexin.menu6.ChatModule.activity.adapter.ViewPageAdapter;
import com.example.yexin.menu6.ChatModule.activity.fragment.FragmentAiTe;
import com.example.yexin.menu6.ChatModule.activity.fragment.FragmentComment;
import com.example.yexin.menu6.ChatModule.activity.fragment.FragmentPrivateChat;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by DELL on 2020/3/28.
 */

public class ConversationChating extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;
    private ImageButton ib_back;
    private ImageButton ib_delete;
    private View one;
    private View two;
    private View three;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private List<Fragment> fragmentList;
    private FragmentPrivateChat fragmentPrivateChat;
    private FragmentComment fragmentComment;
    private FragmentAiTe fragmentAiTe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatdemo_activity);

        InitView();

        InitData();

    }

    private void InitData(){
        fragmentPrivateChat=new FragmentPrivateChat();
        fragmentComment=new FragmentComment();
        fragmentAiTe=new FragmentAiTe();
        fragmentList.add(fragmentPrivateChat);
        fragmentList.add(fragmentComment);
        fragmentList.add(fragmentAiTe);
        viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(viewPageAdapter);
        viewPager.setCurrentItem(0);
        one.setVisibility(View.VISIBLE);
    }

    private void InitView(){

        tv_one=(TextView)findViewById(R.id.tv_one);
        tv_two=(TextView)findViewById(R.id.tv_two);
        tv_three=(TextView)findViewById(R.id.tv_three);
        ib_back=(ImageButton)findViewById(R.id.jmui_return_btn);
        ib_delete=(ImageButton)findViewById(R.id.jmui_right_btn);
        one=(View)findViewById(R.id.one);
        two=(View)findViewById(R.id.two);
        three=(View)findViewById(R.id.three);
        viewPager=(ViewPager)findViewById(R.id.myViewPager);
        fragmentList=new LinkedList<>();

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);
        ib_back.setOnClickListener(this);
        ib_delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                viewPager.setCurrentItem(0);
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                break;
            case R.id.tv_two:
                viewPager.setCurrentItem(1);
                tv_one.setBackgroundColor(Color.WHITE);
                one.setVisibility(View.GONE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.GONE);
                break;
            case R.id.tv_three:
                viewPager.setCurrentItem(2);
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.VISIBLE);
                break;
            case R.id.jmui_return_btn:
                break;
        }
    }

    private void newChat(String data,String mperson,String username,String time,int icon,int num,String mnickname,int allnumber){
        Log.i("在会话界面接收的消息",data+"  "+mperson+"  "+username+"  "+time+"  "+icon+"  "+num+"  "+mnickname);
        Bundle bundle=new Bundle();
        bundle.putString("Data",data);
        bundle.putString("person",mperson);
        bundle.putString("userName",username);
        bundle.putString("time",time);
        bundle.putInt("icon",icon);
        bundle.putInt("num",num);
        bundle.putString("nickname",mnickname);
        bundle.putInt("allnumber",allnumber);
        fragmentPrivateChat.setArguments(bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
