package com.example.yexin.menu6.ChatModule.activity.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexin.menu6.ChatModule.Data.Chating;
import com.example.yexin.menu6.ChatModule.Data.Friends;
import com.example.yexin.menu6.ChatModule.Data.PrivateChatData;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationChating;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationConversation;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationFriend;
import com.example.yexin.menu6.ChatModule.Notification;
import com.example.yexin.menu6.ChatModule.Support.ItemClickSupport;
import com.example.yexin.menu6.ChatModule.activity.ChatingActivity;
import com.example.yexin.menu6.ChatModule.activity.ConversationChating;
import com.example.yexin.menu6.ChatModule.activity.adapter.PrivateChatAdapter;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.R;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by DELL on 2020/3/28.
 */

public class FragmentPrivateChat extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private PrivateChatAdapter privateChatAdapter;
    private LinkedList<PrivateChatData> chatDatas;
    private TextView null_con;
    private OperationFriend operationFriend;
    private OperationConversation operationConversation;
    private OperationChating operationChating;
    private PopupWindow window;
    private String Delete_people;
    private int Delete_positon;



    public static Map<String,Boolean> FragmentPrivateChatifChating=new HashMap<>();  //会话记录
    public static LinkedList<String> FragmentPrivateChatchatlist=new LinkedList<>(); //未读消息


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privatechat_fragment, null);
        recyclerView=(RecyclerView)view.findViewById(R.id.private_list_view);
        null_con=(TextView)view.findViewById(R.id.null_conversation);


        operationChating=new OperationChating(getContext(),UserPublic.getUser());
        operationConversation=new OperationConversation(getContext(),UserPublic.getUser());
        operationFriend=new OperationFriend(getContext(),UserPublic.getUser());


        //注册广播监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshconversation");
        getContext().registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        chatDatas=new LinkedList<>();
        chatDatas.add(new PrivateChatData("我在家","李知恩","14:11",R.drawable.header));
        privateChatAdapter=new PrivateChatAdapter(getContext(),chatDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(privateChatAdapter);

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
                EMClient.getInstance().chatManager().getConversation(chatDatas.get(position).getPeople()).markAllMessagesAsRead();
                FragmentPrivateChat.FragmentPrivateChatifChating.replace(chatDatas.get(position).getPeople(),true);
                for(int i=0;i<FragmentPrivateChat.FragmentPrivateChatchatlist.size();i++){
                    if(chatDatas.get(position).getPeople().equals(FragmentPrivateChat.FragmentPrivateChatchatlist.get(i))){
                        FragmentPrivateChat.FragmentPrivateChatchatlist.remove(i);
                        break;
                    }
                }
                intent.putExtra("user",chatDatas.get(position).getPeople());
                intent.putExtra("Nickname",chatDatas.get(position).getNickName());
                intent.putExtra("icon",chatDatas.get(position).getIconHeader());
                intent.setClass(getContext(),ChatingActivity.class);
                startActivity(intent);
                Log.e("列表点击","你点击了"+chatDatas.get(position).getNickName());
            }
        });
        //长按监听
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                Log.e("列表长按","你长按了"+chatDatas.get(position).getNickName());
                showPopupWindow();
                Delete_people=chatDatas.get(position).getPeople();
                Delete_positon=position;
                return false;
            }
        });

        return view;
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow, null);
        window = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        window.setContentView(contentView);
        //设置各个控件的点击响应
        TextView dialog_delete = (TextView)contentView.findViewById(R.id.dialog_delete);
        TextView dialog_read = (TextView)contentView.findViewById(R.id.dialog_read);
        TextView dialog_top = (TextView)contentView.findViewById(R.id.dialog_top);
        dialog_delete.setOnClickListener(this);
        dialog_read.setOnClickListener(this);
        dialog_top.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.privatechat_fragment, null);
        window.showAtLocation(rootview, Gravity.CENTER, 0, 0);

    }

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String Date=intent.getStringExtra("Data");
            String person=intent.getStringExtra("person");  //添加，用在添加人
            String userName=intent.getStringExtra("userName");
            String time=intent.getStringExtra("time");
            int icon=intent.getIntExtra("icon",R.drawable.header);
            int num=intent.getIntExtra("num",0);
            String nickname=intent.getStringExtra("nickname");
            Boolean isUpdate=intent.getBooleanExtra("isUpdate",false);
            if (action.equals("action.refreshconversation"))
            {
                Toast.makeText(getContext(),"提醒更新消息咯",Toast.LENGTH_SHORT).show();
                if(isUpdate){
                    chatDatas=operationConversation.select();
                    privateChatAdapter.ChangeDate(chatDatas);
                    FragmentPrivateChat.FragmentPrivateChatifChating.clear();
                    for(int i=0;i<chatDatas.size();i++){
                        FragmentPrivateChat.FragmentPrivateChatifChating.put(chatDatas.get(i).getPeople(),false);
                    }
                }
                //newChat(Date,person,userName,time,icon,num,nickname,allnumber);
            }
        }
    };



    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Log.e("fragment","来消息了"+args.get("Data"));
        String Date=args.getString("Data");
        String person=args.getString("person");  //添加，用在添加人
        String userName=args.getString("userName");
        String time=args.getString("time");
        int icon=args.getInt("icon");
        int num=args.getInt("num");
        int allnumber=args.getInt("allnumber");
        String nickname=args.getString("nickname");

    }

    @Override
    public void onResume() {
        super.onResume();
        chatDatas=operationConversation.select();
        privateChatAdapter.ChangeDate(chatDatas);
        FragmentPrivateChat.FragmentPrivateChatifChating.clear();
        for(int i=0;i<chatDatas.size();i++){
            FragmentPrivateChat.FragmentPrivateChatifChating.put(chatDatas.get(i).getPeople(),false);
        }
    }

    private void addMessage(){
        chatDatas.add(new PrivateChatData("我喜欢你","李沁","15:11",R.drawable.a2));
        chatDatas.add(new PrivateChatData("我爱你","女朋友","11:11",R.drawable.a3));
        chatDatas.add(new PrivateChatData("我在拍戏","angelabody","11:11",R.drawable.a4));
        chatDatas.add(new PrivateChatData("极限挑战","迪丽热巴","11:11",R.drawable.a5));
        chatDatas.add(new PrivateChatData("仙剑奇侠","刘亦菲","11:11",R.drawable.a6));
        chatDatas.add(new PrivateChatData("在干嘛啊","高圆圆","11:11",R.drawable.a7));
        chatDatas.add(new PrivateChatData("萨拉哟嘿","林允儿","11:11",R.drawable.a8));
        chatDatas.add(new PrivateChatData("商演呢","鞠婧祎","11:11",R.drawable.a9));
        chatDatas.add(new PrivateChatData("滴滴，在吗？","韩雪","11:11",R.drawable.a12));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_delete:
                deleteChat();
                Toast.makeText(getContext(),"点击了删除会话",Toast.LENGTH_SHORT).show();
                window.dismiss();
                break;
            case R.id.dialog_read:
                Toast.makeText(getContext(),"点击了标记已读",Toast.LENGTH_SHORT).show();
                EMClient.getInstance().chatManager().markAllConversationsAsRead();
                window.dismiss();
                break;
            case R.id.dialog_top:
                Toast.makeText(getContext(),"点击了会话置顶",Toast.LENGTH_SHORT).show();
                window.dismiss();
                break;
//            case R.id.create_group_btn:
//                Intent intent=new Intent();
//                intent.setClass(this,search_activity.class);
//                startActivity(intent);
//                break;
        }
    }

    private void deleteChat(){
        operationChating.Delete(Delete_people,UserPublic.getUser());
        operationConversation.Delete(Delete_people);
        chatDatas=privateChatAdapter.deleteItem(Delete_positon);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
