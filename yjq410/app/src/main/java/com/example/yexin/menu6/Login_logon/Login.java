package com.example.yexin.menu6.Login_logon;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexin.menu6.Common.Json.Web_Json;
import com.example.yexin.menu6.Common.Public.ChackOperation;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.Common.Public.User_public;
import com.example.yexin.menu6.Common.Refresh.RefreshDialog;
import com.example.yexin.menu6.Common.Url.Web_url;
import com.example.yexin.menu6.Index.MainActivity;
import com.example.yexin.menu6.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class Login extends Activity {
    private TextView tv_register;
    private EditText text_username;
    private EditText text_password;
    private Button button_submit;
    private String UserId=null;
    private String UserPassword=null;
    private TextView WelcomeTitle;
    private User_public user_public=null;

    private RefreshDialog refreshDialog;
    private int Refresh_status=2;
    private final int Refresh_Success=1;
    private final int Refresh_Fail=0;


    //权限申请
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS,Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE})
    public void getSingle() {
        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
    }
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS,Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE})
    public void showSingleRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("为了正常使用，将获取您的一些权限信息")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        }).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_public=new User_public(this);
        setContentView(R.layout.activity_login);
        LoginPermissionsDispatcher.getSingleWithPermissionCheck(this);
        Layout_init();
    }



    public void Layout_init(){
        tv_register = (TextView)findViewById(R.id.tv_register);
        text_username=(EditText)findViewById(R.id.et_username);
        text_password=(EditText)findViewById(R.id.et_password);
        button_submit=(Button)findViewById(R.id.btn_login);
        WelcomeTitle=(TextView)findViewById(R.id.welcom_title);
        WelcomeTitle.setTypeface(Typeface.createFromAsset(getAssets(),"ssssss.ttf"));
        button_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                refreshContent();

            }
        });
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_register:
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                //finish();
                break;
            default:
        }
    }

    private void refreshContent() {
        refreshDialog = new RefreshDialog(Login.this,"正在登陆...",R.mipmap.ic_dialog_loading);
        refreshDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    http();
                    //Intent intent = new Intent(Login.this, MainActivity.class);
                    //startActivity(intent);

                    refreshDialog.dismiss();
                    Refresh_status=Refresh_Fail;
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((Activity)Login.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在之中可以终止线程
                    }
                });
            }
        }).start();
    }
    private void http(){
        if (!TextUtils.isEmpty(text_username.getText().toString())&& !TextUtils.isEmpty(text_password.getText().toString())) {
            Log.e("WangJ", "都不空");
            UserId= text_username.getText().toString();
            UserPassword = text_password.getText().toString();


            //在密码不为空的基础上请求网络资源
            String jsonObject = Web_Json.Login(UserId, UserPassword);
            RequestParams params = new RequestParams(Web_url.URL_Login);
            params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部
            params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
            params.setBodyContent(jsonObject);//添加json内容到请求参数里
            x.http().post(params, new Callback.CommonCallback<String>() {
                private Boolean isSuccess=false;
                @Override
                public void onSuccess(String result) {

                    Log.e("yjq", "网络请求成功" + result);  //接收JSON的字符串
                    HashMap<String, String> map = Web_Json.getJson(result);
                    user_public.setUser_flag(true);
//                            UserPublic.setUser_flag(true);
                    user_public.setUser(UserId);
//                            UserPublic.setUser(UserId);
                    user_public.setUser_str(map.get("token"));
//                            UserPublic.setUser_str(map.get("token"));
                    user_public.setIcon(map.get("Picture"));
//                            UserPublic.setIcon(map.get("Picture"));
                    user_public.setUser_n(map.get("NickName"));
//                            UserPublic.setUser_n(map.get("NickName"));
                    user_public.setUser_level(map.get("Level"));
//                            UserPublic.setUser_level(map.get("Level"));
                    user_public.setPassword(text_password.getText().toString());

                    if("500".equals(map.get("code"))) {
                        isSuccess = false;
                    }else if("200".equals(map.get("code"))){
                        isSuccess = true;
                    }
                    Log.e("yjq1", "编码:" + map.get("code") + map.get("message" + map.get("token")));
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("yjq1", "失败");

                    refreshDialog.dismiss();
                    Refresh_status=Refresh_Fail;
                    Toast.makeText(Login.this, "登录失败，网络连接超时！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.e("yjq", "取消");
                }

                @Override
                public void onFinished() {
                    Log.e("yjq", "完成");

//                            user_public.setFirst(false);  //是从登入去的，不需要更新长连接
                    new ChackOperation(Login.this).setCheckingFalse();


                    if(isSuccess){
                        refreshDialog.dismiss();
                        Refresh_status=Refresh_Success;
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                        refreshDialog.dismiss();
                        Refresh_status=Refresh_Fail;
                        finish();
                    }else{
                        refreshDialog.dismiss();
                        Refresh_status=Refresh_Fail;
                        Toast.makeText(Login.this, "账号密码错误", Toast.LENGTH_SHORT).show();
                        refreshDialog.dismiss();
                        Refresh_status=Refresh_Fail;
                    }
                }
            });
        } else {
            refreshDialog.dismiss();
            Refresh_status=Refresh_Fail;
            Log.e("WangJ", "都空");
            Toast.makeText(Login.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
        }

    }

}
