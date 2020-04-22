package com.example.yexin.menu6.Index;


import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;

import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
//import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexin.menu6.Ballculb.Payfaceture;
import com.example.yexin.menu6.ChatModule.Data.Chating;
import com.example.yexin.menu6.ChatModule.Data.Friends;
import com.example.yexin.menu6.ChatModule.Data.PrivateChatData;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationChating;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationConversation;
import com.example.yexin.menu6.ChatModule.DataBaseOperation.OperationFriend;
import com.example.yexin.menu6.ChatModule.Notification;
import com.example.yexin.menu6.ChatModule.activity.ConversationChating;
import com.example.yexin.menu6.ChatModule.activity.fragment.FragmentPrivateChat;
import com.example.yexin.menu6.Common.Public.ChackOperation;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.Common.Public.User_public;
import com.example.yexin.menu6.Index.Collect.FocusActivity;
import com.example.yexin.menu6.Order.OrderFormActivity;
import com.example.yexin.menu6.Sideslip.Sideslip_center.Share.Tickling;
import com.example.yexin.menu6.Sideslip.Sideslip_top.Cityselect.CityPickerActivity;
import com.example.yexin.menu6.R;
import com.example.yexin.menu6.Sideslip.Sideslip_top.Head_photo.PhotoImageView;
import com.example.yexin.menu6.Person_Information.Activitys.PersonInformationActivity;
import com.example.yexin.menu6.Sideslip.Sideslip_center.Setting.SettingActivity;
import com.example.yexin.menu6.Sideslip.Sideslip_center.Share.Sideslip_share;
import com.example.yexin.menu6.Common.Thread.Thread_one;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.yexin.menu6.R.id.imageView_sideslip;

public class MainActivity extends AppCompatActivity  implements OnBannerListener,EMMessageListener,NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView navigation=null;
    private Thread_one thread_one=null;
    private Thread thread=null;
    private ImageView UserHead=null;
    private TextView UserName;
    private ImageView UserLevel=null;
    private static TextView tv_locate_city;
    private static PhotoImageView imageview_sideslip;
    private User_public user_public=null;
    private ImageView iv_chat;
    private LinkedList<Integer> headerlist=new LinkedList<>();
    private OperationFriend operationFriend;
    private OperationConversation operationConversation;
    private OperationChating operationChating;




    /*底部导航栏按钮监听事件*/
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_games:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_friends:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void OnBannerClick(int position) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.c4));
        }
        super.onCreate(savedInstanceState);
        user_public=new User_public(this);
        headerlist.add(R.drawable.a1);
        headerlist.add(R.drawable.a2);
        headerlist.add(R.drawable.a3);
        headerlist.add(R.drawable.a4);
        headerlist.add(R.drawable.a5);
        headerlist.add(R.drawable.a6);
        headerlist.add(R.drawable.a7);
        headerlist.add(R.drawable.a8);
        headerlist.add(R.drawable.a9);
        headerlist.add(R.drawable.a10);
        headerlist.add(R.drawable.a11);
        headerlist.add(R.drawable.a12);
        headerlist.add(R.drawable.header);
        operationChating=new OperationChating(this,UserPublic.getUser());
        operationConversation=new OperationConversation(this,UserPublic.getUser());
        operationFriend=new OperationFriend(this,UserPublic.getUser());

        Judge();
        setContentView(R.layout.activity_main);
//        if(EMClient.getInstance().isLoggedInBefore()){
//            Log.e("已经登入","登入了");
//        }else{
//            String IMuser=null;
//            String IMpassword=null;
//            if(UserPublic.getUser()==null){
//                IMuser="15179038625";
//                IMpassword="123123";
//            }else{
//                IMuser=UserPublic.getUser();
//                IMpassword=UserPublic.getUser_password();
//            }
//            EMClient.getInstance().login(IMuser,IMpassword,new EMCallBack() {//回调
//                @Override
//                public void onSuccess() {
//                    EMClient.getInstance().groupManager().loadAllGroups();
//                    EMClient.getInstance().chatManager().loadAllConversations();
//                    Log.e("Login_activity_1", "登录聊天服务器成功！");
//                    Looper.prepare();
//                    Toast.makeText(MainActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
//                }
//
//                @Override
//                public void onProgress(int progress, String status) {
//
//                }
//
//                @Override
//                public void onError(int code, String message) {
//                    Log.e("login_activity_2", "登录聊天服务器失败！");
//                    Looper.prepare();
//                    Toast.makeText(MainActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
//                }
//            });
//        }


        viewPager = (ViewPager)findViewById(R.id.viewpager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_chat=(ImageView)findViewById(R.id.chat);
        iv_chat.setOnClickListener(this);
        TextView tv_search = (TextView)findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_search= new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent_search);
            }
        });
        Intent intent=getIntent();
        /*设置控件监听器*/
        View parentView;
        setSupportActionBar(toolbar);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //预设三个fragment
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        /*修改toggle原有顶部button*/
        toggle.syncState();/*显示button*/
        toggle.setDrawerIndicatorEnabled(false);/*取消原有button*/
        toggle.setHomeAsUpIndicator(R.drawable.cehua);/*添加自己的button*/
        toggle.setToolbarNavigationClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


//        UserLevel.setText(UserPublic.getUser_level());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);
        UserName = (TextView) header.findViewById(R.id.UserName);
        UserLevel=(ImageView) header.findViewById(R.id.UserLevel);
        UserHead=(ImageView)header.findViewById(R.id.imageView_sideslip);
        //UserName.setText(UserPublic.getUser_n());
        UserName.setText("杨家齐");
        UserName.setTypeface(Typeface.createFromAsset(getAssets(),"ssssss.ttf"));
        //UserLevel.setText(UserPublic.getUser_level());
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new oneFragment());
        adapter.addFragment(new twoFragment());
        adapter.addFragment(new threeFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_indent) {
            Intent intent_indent=new Intent(MainActivity.this, OrderFormActivity.class);
            startActivity(intent_indent);
        } else if (id == R.id.nav_wallet) {
            UserHead.setImageDrawable (getResources().getDrawable(R.drawable.header));
            Toast.makeText(this,"2",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_erweima) {

            Toast.makeText(this,"3",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            startActivity(Sideslip_share.share());
        } else if (id == R.id.nav_yijianfankui) {
            Toast.makeText(this,"5",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,Tickling.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Toast.makeText(this,"6",Toast.LENGTH_LONG).show();
        } else if (id ==R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_wodeshouchan) {
            Intent intent = new Intent(MainActivity.this,FocusActivity.class);
            startActivity(intent);
            Toast.makeText(this,"收藏",Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onClick(View view){
        switch(view.getId()) {
            case imageView_sideslip:
                Intent intent_one = new Intent(MainActivity.this, PersonInformationActivity.class);
                startActivity(intent_one);
                break;
            case R.id.tv_locate_city:
                tv_locate_city = (TextView) findViewById(R.id.tv_locate_city);
                // tv_locate_city.setText("南昌shi");
                Intent intent1 = new Intent(MainActivity.this, CityPickerActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.chat:
                Intent intent = new Intent(this, ConversationChating.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.w("base:requestCode","wd"+requestCode);
        super.onActivityResult( requestCode,  resultCode,  data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    if(data!=null)
                    {
                        tv_locate_city.setText(data.getStringExtra("city_name")+"市");
                    }
                }
                break;

            case 3:
                break;

            case 2:
                if (resultCode == RESULT_OK) {
                    handleImage(data);

                }
                break;
            case 4:
                break;

            default:
                break;

        }
    }

    // 只在Android4.4及以上版本使用
    //@TargetApi(19)
    private void handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 通过document id来处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                // 解析出数字id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equals(uri.getScheme())) {
            // 如果不是document类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        Log.w("base:uri",uri.toString());
        String path = null;
        // 通过Uri和selection来获取真实图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                Log.w("path",path);
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageview_sideslip.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "失败 to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        EMClient.getInstance().chatManager().removeMessageListener(this);
        Log.e("yjq123","杨家齐，MainActivity已经注销");
    }

    public void Judge(){
//        ChackOperation chackOperation=new ChackOperation(MainActivity.this);
//        boolean isChecking=chackOperation.getCheckingResult();
//        chackOperation.setCheckingFalse();
//        Log.e("MainActivity，是否需要长连接",""+ isChecking);
//        if(isChecking){
////            thread_one=new Thread_one(MainActivity.this,UserName,UserLevel,UserHead);
////            thread=new Thread(thread_one);
////            thread.run();
//        }
    }

    //注册消息监听，当接收到消息的时候发送广播监听，提醒chademo处理消息
    @Override
    public void onMessageReceived(List<EMMessage> list){
        Log.e("得到的消息",list.toString());
        Log.e("消息列表",list.size()+"");
        Log.e("获得的消息",list.get(0).toString());
        for(final EMMessage message:list){


                    //收到的信息
                    String Date=((EMTextMessageBody)message.getBody()).getMessage();
                    //发信息的人
                    String person=message.getFrom();
                    //消息昵称
                    String userName=message.getUserName();
                    //设置日期格式
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                    String time=df.format(new Date());// new Date()为获取当前系统时间
                    //消息数量
                    int number= EMClient.getInstance().chatManager().getConversation(person).getUnreadMsgCount();
                    int allnumber=EMClient.getInstance().chatManager().getUnreadMessageCount();


            Log.e("消息接收","内容"+Date+"发送方账号"+person+"发送方昵称"+userName+"时间"+time+"未读的消息数量"+number+"头像");
            Log.e("接收的消息","内容"+Date+"人"+person+"userName"+userName+"时间"+time+"会话消息数量"+number+"所有消息数量"+allnumber);

            Friends ChatingPeople;
            if(operationConversation.find(person)){
                Log.e("会话","新会话");
               // ChatingPeople=newChat(Date,time,number+"",person,userName);
            }else{
                Log.e("会话","已有会话");
               // ChatingPeople=oldChat(Date,time,number+"",person,userName);
            }

            if(!FragmentPrivateChat.FragmentPrivateChatifChating.isEmpty()&&FragmentPrivateChat.FragmentPrivateChatifChating.containsKey(person)&&FragmentPrivateChat.FragmentPrivateChatifChating.get(person)){
                Log.w("是否正在聊天","正在聊天");
                Intent intent1=new Intent();
                intent1.putExtra("Data",Date);
                intent1.putExtra("person",person);
                intent1.putExtra("userName",userName);
                intent1.putExtra("time",time);
                intent1.putExtra("type",1);
                //intent1.putExtra("icon",ChatingPeople.getIcon());
                intent1.setAction("action.refreshChating");
                sendBroadcast(intent1);
            }else{
                Log.w("是否正在聊天","没有在聊天");
                int index=0;
                for(int i=0;i<FragmentPrivateChat.FragmentPrivateChatchatlist.size();i++){
                    if(FragmentPrivateChat.FragmentPrivateChatchatlist.get(i).equals(person)){
                        index=i;
                        break;
                    }
                }
                Log.w("未读的消息数量",""+index);
                if(index==FragmentPrivateChat.FragmentPrivateChatchatlist.size()){
                    FragmentPrivateChat.FragmentPrivateChatchatlist.add(person);
                }

                Notification.chatNotification(this,allnumber,FragmentPrivateChat.FragmentPrivateChatchatlist.size(),1);
            }
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onGroupMessageRead(List<EMGroupReadAck> list) {

    }

    @Override
    public void onReadAckForGroupMessageUpdated() {
    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }


    /**
     * 来新消息的使用
     * @param Date
     * @param time
     * @param number
     * @param people
     */
//    private Friends newChat(String Date,String time,String number,String people,String userName){
//        PrivateChatData a;
//        Chating b;
//        Friends friends;
//        if(operationFriend.isFind(people)){
//            /**没有的话，要创建表，和添加好友
//             * 创建表
//             */
//            Random random=new Random();
//            int a12=random.nextInt(14);
//            int micon=headerlist.get(a12);
//
//
//            Log.e("是否为好友","不是好友创建数据库，以及插入好友表名字："+userName+"账号"+people+"头像"+micon);
//            friends=new Friends(userName,people,micon);
//            operationFriend.Insert(friends);
//            //会话实体
//            a=new PrivateChatData(people,Date,number,"0",time,userName,micon);
//            //聊天记录实体
//            b=new Chating(Date,time,1,UserPublic.getUser()+people,micon);
//
//
//            sendUpdateMessage(Date,time,number,people,userName,micon);
//        }else{
//            friends=operationFriend.find(people);
//            a=new PrivateChatData(people,Date,number,"0",time,friends.getName(),friends.getIcon());
//            //聊天记录实体
//            b=new Chating(Date,time,1,UserPublic.getUser()+people,friends.getIcon());
//            sendUpdateMessage(Date,time,number,people,userName,friends.getIcon());
//        }
//        //判断是否有这个好友
//        /*
//        因为是新的聊天，所以要添加这个会话，并且把聊天记录存储到相应的表内，并更新适配器的内容
//         */
//        Log.e("是否为好友","是好友");
//        operationConversation.Insert(a);
//        operationChating.Insert(b);
//        return friends;
//    }

    /**
     * 在已有会话上跟新
     * @param Date
     * @param time
     * @param number
     * @param people
     */
//    private Friends oldChat(String Date,String time,String number,String people,String userName){
//        Friends findfriend=operationFriend.find(people);
//        //会话实体
//        PrivateChatData a=new PrivateChatData(people,Date,number,"0",time,userName,findfriend.getIcon());
//        //消息记录实体
//        Chating b=new Chating(Date,time,1,UserPublic.getUser()+people,findfriend.getIcon());
//        //更新会话的内容
//        operationConversation.Receive(a);
//        //插入消息到相应的表中
//        operationChating.Insert(b);
//        //适配数据
//        if(!FragmentPrivateChat.FragmentPrivateChatifChating.isEmpty()&&FragmentPrivateChat.FragmentPrivateChatifChating.containsKey(people)&&FragmentPrivateChat.FragmentPrivateChatifChating.get(people)){
//            ;
//        }else{
//            sendUpdateMessage(Date,time,number,people,userName,findfriend.getIcon());
//        }
//        return findfriend;
//    }

//    private void sendUpdateMessage(String Date,String time,String number,String people,String userName,int micon){
//        Log.i("MainActivity","来消息了，通过广播发送消息");
//        Intent intent=new Intent();
//        intent.putExtra("Data",Date);
//        intent.putExtra("person",people);
//        intent.putExtra("userName",userName);
//        intent.putExtra("time",time);
//        intent.putExtra("icon",micon);
//        intent.putExtra("num",number);
//        intent.putExtra("nickname","");
//        intent.putExtra("isUpdate",true);
//        intent.setAction("action.refreshconversation");
//        sendBroadcast(intent);
//    }
}
