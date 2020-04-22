package com.example.yexin.menu6.ChatModule.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexin.menu6.ChatModule.Data.Chating;
import com.example.yexin.menu6.ChatModule.Data.PrivateChatData;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationChating;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationConversation;
import com.example.yexin.menu6.ChatModule.KeyBoards.Common.adapter.ChattingListAdapter;
import com.example.yexin.menu6.ChatModule.KeyBoards.Common.widget.SimpleAppsGridView;
import com.example.yexin.menu6.ChatModule.KeyBoards.Constants;
import com.example.yexin.menu6.ChatModule.KeyBoards.SimpleCommonUtils;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.sj.emoji.EmojiBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import sj.keyboard.XhsEmoticonsKeyBoard;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

/**
 * Created by DELL on 2020/3/29.
 */

public class ChatingActivity extends Activity implements FuncLayout.OnFuncKeyBoardListener {
    private ImageButton ib_back;
    private TextView tv_title;
    private ImageButton im_info;
    private ListView lv_chating;
    private ImageView iv_voice;
    private EditText et_text;
    private ImageView iv_emoji;
    private ImageView iv_app;
    private Button b_send;
    private LinkedList<Chating> list;
    private String account;
    private String Nickname;
    private int icon;
    private XhsEmoticonsKeyBoard ekBar;;
    private ChattingListAdapter chattingListAdapter;
    private OperationChating operationChating;
    private OperationConversation operationConversation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.chating_activity);

        Intent intentReceive=getIntent();
        account=intentReceive.getStringExtra("user");
        Nickname=intentReceive.getStringExtra("Nickname");
        icon=intentReceive.getIntExtra("icon",R.drawable.header);
        Log.e("界面传递",account+"  "+Nickname+"   "+icon);

        InitBroadcastReceiver();

        InitData();

        initImageKeyBoard();

        initListView();
    }

    private void InitBroadcastReceiver(){
        //注册广播监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshChating");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    private void initImageKeyBoard(){
        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));
        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.addFuncView(new SimpleAppsGridView(this));

        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }
        });
        ekBar.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加表情
            }
        });
        ekBar.getEmoticonsToolBarView().addToolItemView(R.mipmap.icon_setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //表情设置
            }
        });
    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if(o == null){
                    return;
                }
                if(actionType == Constants.EMOTICON_CLICK_BIGIMAGE){
                    if(o instanceof EmoticonEntity){
                        OnSendImage(((EmoticonEntity)o).getIconUri());
                    }
                } else {
                    String content = null;
                    if(o instanceof EmojiBean){
                        content = ((EmojiBean)o).emoji;
                    } else if(o instanceof EmoticonEntity){
                        content = ((EmoticonEntity)o).getContent();
                    }

                    if(TextUtils.isEmpty(content)){
                        return;
                    }
                    int index = ekBar.getEtChat().getSelectionStart();
                    Editable editable = ekBar.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(EmoticonsKeyboardUtils.isFullScreen(this)){
            boolean isConsum = ekBar.dispatchKeyEventInFullScreen(event);
            return isConsum ? isConsum : super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }



    //在这里发送消息
    private void OnSendBtnClick(String msg) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        String time=df.format(new Date());// new Date()为获取当前系统时间
        if (!TextUtils.isEmpty(msg)) {
            Chating bean = new Chating();
            bean.setContent(msg);
            bean.setTime(time);
            bean.setName(UserPublic.getUser()+account);
            bean.setType(0);
            bean.setIcon(R.drawable.header);
            chattingListAdapter.addData(bean, true, false);
            Log.e("发送的消息","消息"+bean.getContent()+"   时间"+bean.getTime()+"    名称"+bean.getName()+"   类型"+bean.getType());
            operationChating.Insert(bean);
            SendMessage(bean);
            scrollToBottom();
        }
        else{
            Toast.makeText(ChatingActivity.this, "不能发送空消息！", Toast.LENGTH_SHORT).show();
        }
    }

    private void OnSendImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            OnSendBtnClick("[img]" + image);
        }
    }

    private void scrollToBottom() {
        lv_chating.requestLayout();
        lv_chating.post(new Runnable() {
            @Override
            public void run() {
                lv_chating.setSelection(lv_chating.getBottom());
            }
        });
    }

    private void initListView() {
        chattingListAdapter = new ChattingListAdapter(this);
        LinkedList<Chating> beanList;
        beanList=operationChating.select(account,UserPublic.getUser());
        for (int i = 0; i < 20; i++) {
            Chating bean = new Chating();
            bean.setContent("Test:" + i);
            if((i+1)%2==0){
                bean.setType(0);
            }else{
                bean.setType(1);
            }
            beanList.addFirst(bean);
        }
        chattingListAdapter.addData(beanList);
        lv_chating.setAdapter(chattingListAdapter);
        lv_chating.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        ekBar.reset();
                        break;
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }


    public void InitData(){
        /*
        初始化
         */
        ekBar=(XhsEmoticonsKeyBoard)findViewById(R.id.chat_view);
        ib_back=(ImageButton)findViewById(R.id.jmui_return_btn);
        tv_title=(TextView)findViewById(R.id.jmui_group_num_tv);
        im_info=(ImageButton)findViewById(R.id.jmui_right_btn);
        lv_chating=(ListView)findViewById(R.id.lv_chat);
//        rv_chating=(RecyclerView)findViewById(R.id.lv_chat);
        iv_voice=(ImageView)findViewById(R.id.btn_voice_or_text);
        et_text=(EditText)findViewById(R.id.et_chat);
        iv_emoji=(ImageView)findViewById(R.id.btn_face);
        iv_app=(ImageView)findViewById(R.id.btn_multimedia);
        b_send=(Button)findViewById(R.id.btn_send);
        operationChating=new OperationChating(this,UserPublic.getUser());
        operationConversation=new OperationConversation(this,UserPublic.getUser());
        tv_title.setText(Nickname);
//        查询数据，实例化适配器


        /*
        设置监听器
         */
//        ib_back.setOnClickListener(this);
//        im_info.setOnClickListener(this);
//        iv_voice.setOnClickListener(this);
//        iv_emoji.setOnClickListener(this);
//        iv_app.setOnClickListener(this);
//        b_send.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() { }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播监听
        unregisterReceiver(mRefreshBroadcastReceiver);
        Log.e("finsh","界面退出");
    }



    //发送消息
    private void SendMessage(Chating msg){
        EMMessage message = EMMessage.createTxtSendMessage(msg.getContent(), account);
        message.setChatType(EMMessage.ChatType.Chat);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("IMone", "发送成功"+account);
                PrivateChatData a=new PrivateChatData(account,msg.getContent(),"0","0",msg.getTime(),Nickname,icon);
//                dbChating.Insert(msg);
                if(operationConversation.find(account)){
                    //为空
                    operationConversation.Insert(a);
                }else{
                    operationConversation.Receive(a);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("IMtwo", "发送失败");
                Looper.prepare();
                Toast.makeText(ChatingActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ekBar.reset();
    }

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String Date=intent.getStringExtra("Data");
            String person= UserPublic.getUser()+intent.getStringExtra("person");  //添加，用在添加人
            String userName=intent.getStringExtra("userName");
            String time=intent.getStringExtra("time");
            int type=intent.getIntExtra("type",1);
            int icon=intent.getIntExtra("micon",R.drawable.header);
            if (action.equals("action.refreshChating"))
            {
                Toast.makeText(ChatingActivity.this,"来了这个界面的消息",Toast.LENGTH_SHORT).show();
                Log.e("得到消息","消息内容"+Date+"  "+"消息人"+"   "+person+"   "+"时间"+time);
                Chating chating=new Chating(Date,time,type,person,icon);
                reFreshChating(chating);
            }
        }
    };
    private void reFreshChating(Chating a){
        chattingListAdapter.addData(a,true,false);
    }


}
