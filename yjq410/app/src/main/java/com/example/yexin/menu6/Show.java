package com.example.yexin.menu6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yexin.menu6.Common.Public.ChackOperation;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.Common.Public.User_public;
import com.example.yexin.menu6.Index.MainActivity;
import com.example.yexin.menu6.Login_logon.Login;

/**
 * Created by DELL on 2019/10/17.
 * @OnPermissionDenied注解：这个也不是必须的注解，用于标注如果权限请求失败，
 * 但是用户没有勾选不再询问的时候执行的方法，注解括号里面有参数，传入想要申请的权限。
 * 也就是说，我们可以在这个方法做申请权限失败之后的处理，如像用户解释为什么要申请，或者重新申请操作等。
 * @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})//一旦用户拒绝了
 *  public void multiDenied() {
 *  Toast.makeText(this, "已拒绝一个或以上权限", Toast.LENGTH_SHORT).show();
 *  }
 * @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)//一旦用户拒绝了
 * public void StorageDenied() {
 * Toast.makeText(this, "已拒绝WRITE_EXTERNAL_STORAGE权限", Toast.LENGTH_SHORT).show();
 *}
 *
 * @OnNeverAskAgain注解：这个也不是必须的注解，用于标注如果权限请求失败,而且用户勾选不再询问的时候执行的方法，注解括号里面有参数，传入想要申请
 * 的权限。也就是说，我们可以在这个方法做申请权限失败并选择不再询问之后的处理。例如，可以告诉作者想开启权限的就从手机设置里面开启。
 * @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})//用户选择的不再询问
 * public void multiNeverAsk() {
 * Toast.makeText(this, "已拒绝一个或以上权限，并不再询问", Toast.LENGTH_SHORT).show();
 * }
 * @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)//用户选择的不再询问
 * public void StorageNeverAsk() {
 * Toast.makeText(this, "已拒绝WRITE_EXTERNAL_STORAGE权限，并不再询问", Toast.LENGTH_SHORT).show();
 * }
 */

public class Show extends Activity implements View.OnClickListener {
    private User_public user_public=null;
    private ImageView imageView=null;

    private Context mContext;
    private int time=3;
    private TextView li_time;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    if(time>0) {
                        li_time.setText("跳过"+time);
                        time--;
                        handler.sendMessageDelayed(handler.obtainMessage(1),1000);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /**
     * 权限申请
     * android.permission.BROADCAST_SMS，当收到短信时触发一个广播
     * android.permission.BRICK，能够禁用手机，非常危险，顾名思义就是让手机变成砖头
     * android.permission.CALL_PHONE，允许程序从非系统拨号器里输入电话号码
     * android.permission.CALL_PRIVILEGED，允许程序拨打电话，替换系统的拨号器界面
     * android.permission.CAMERA，允许访问摄像头进行拍照
     * android.permission.READ_CONTACTS，允许应用访问联系人通讯录信息
     * android.permission.READ_FRAME_BUFFER，读取帧缓存用于屏幕截图
     * android.permission.READ_SMS，读取短信内容
     * android.permission.RECEIVE_SMS，接收短信
     * android.permission.WRITE_EXTERNAL_STORAGE 用于写入缓存数据到扩展存储卡
     * android.permission.READ_EXTERNAL_STORAGE 于读取扩展存储卡
     * android.permission.INTERNET 用于访问网络，网络定位需要上网
     * android.permission.ACCESS_FINE_LOCATION 用于访问GPS定位  允许一个程序访问精良位置(如GPS)
     */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        li_time=(TextView)findViewById(R.id.li_text);
        li_time.setOnClickListener(this);
        mContext = this;
        handler.sendMessageDelayed(handler.obtainMessage(1), 1000);
        //延迟3秒后进入主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                function();
            }
        },5000);
    }
    private void function(){
        user_public=new User_public(this);
        Boolean isFirstIn=new ChackOperation(Show.this).getCheckingResult();
        if(isFirstIn){
            Log.e("Show","第一次登入,跳转登入界面"+"   创建xml文件");
            user_public.CreateShareFil();
            new ChackOperation(Show.this).setCheckingTrue();
            goToRegisterAndLoginActivity();
        }
        else{
            user_public.getShareFileContent();
            if(UserPublic.isUser_flag()){           //进入主界面，暂注释
                Log.e("Show","登入状态，进入主页检查长连接");
                user_public.getShareFileContent();  //初始化个人信息
                goToMainActivity();
            }
            else{
                Log.e("Show","不在登入状态，去登入");
                goToRegisterAndLoginActivity();
            }
        }
    }

    @Override
    public void onClick(View view) {
        function();
    }


    private void goToMainActivity() {
        Intent intent = new Intent(Show.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToRegisterAndLoginActivity() {
        Intent intent = new Intent(Show.this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("Show","show界面已经注销，申请权限");
        handler.removeCallbacksAndMessages(null);
    }
}
