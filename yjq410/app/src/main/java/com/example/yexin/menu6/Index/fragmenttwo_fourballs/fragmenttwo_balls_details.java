package com.example.yexin.menu6.Index.fragmenttwo_fourballs;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.Common.Refresh.RefreshDialog;
import com.example.yexin.menu6.Common.Url.Web_url;
import com.example.yexin.menu6.Index.MainActivity;
import com.example.yexin.menu6.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class fragmenttwo_balls_details extends Activity {



    private RelativeLayout rl_competition_content;
    private ImageView iv_competition_back;
    private ImageView iv_share;
    private TextView tv_competition_group_booking_message;
    private TextView tv_game_time;
    private ImageView iv_locale;
    private TextView tv_competition_announcer;
    private TextView tv_competition_type;
    private TextView tv_competition_announce_time;
    private TextView tv_competition_time;
    private TextView tv_competition_place;
    private TextView tv_competition_note;
    private  RelativeLayout rl_join;

    private String gamedetailsid;
    private String title;
    private String phone;
    private JSONArray jsonArr;
    private JSONObject jsonObject=null;

    private RefreshDialog refreshDialog;
    private int Refresh_status=2;
    private final int Refresh_Success=1;
    private final int Refresh_Fail=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmenttwo_balls_details);
        Intent intent =getIntent();
        gamedetailsid=intent.getStringExtra("gamedetailsid");
        title=intent.getStringExtra("title");
        Log.e("gamedetailsid",gamedetailsid);
        if(gamedetailsid!=null){
            try{
                jsonObject=new JSONObject();
                jsonObject.put("gamedetailsid",gamedetailsid);
            }catch(Exception e){
                e.printStackTrace();
            }
            http(Web_url.URL_Getgamedetails,jsonObject);
        }

        initial();
    }
    private void AddData(String result ){

        JSONObject jsonObjectadd=new JSONObject();
        try{
            //int jsonSize = result.length();//获取数据组的长度
            jsonArr=new JSONArray(result);
            for(int i=0;i<jsonArr.length();i++){

                Log.e("yjqresult:","jsonArr长度："+jsonArr.length());
                jsonObjectadd = (JSONObject)jsonArr.getJSONObject(i);
                Log.e("yjqresult:",i+"jsonObject："+jsonObject.toString());
                Log.e("数据的变化",jsonObjectadd.getString("id"));
                Log.e("数据的变化",jsonObjectadd.getString("type"));
                dataInstance(jsonObjectadd.getString("organizer"),jsonObjectadd.getString("type"),jsonObjectadd.getString("hosttime"),
                        jsonObjectadd.getString("hostplace"),jsonObjectadd.getString("applytime"),jsonObjectadd.getString("games"),jsonObjectadd.getString("phone"));
            }
                   /*
                   * 此处不能运行*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void http(final String URL, final JSONObject jsonObjecthttp){

        RequestParams params = new RequestParams(URL);
        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部
        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)

         params.setBodyContent(jsonObjecthttp.toString());//添加json内容到请求参数里
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("yjq","网络请求成功"+result);  //接收JSON的字符串
//               HashMap<String,String> map=Web_Json.getJson(result);
                //HashMap<String,String> map=Web_Json.getJson(result);
                Log.e("yjqresult:",result.toString());
                Log.e("yjqresult:","长度："+result.length());
                if(result!=null){
                    if(Web_url.URL_Getgamedetails.equals(URL)){
                        AddData(result);
                    }else if(Web_url.URL_AddJoinGame.equals(URL))
                        Log.e("addjoingame","结果："+result);
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("yjq1","失败");
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("yjq","取消");
            }
            @Override
            public void onFinished() {
                Log.e("yjq","完成");
                refreshDialog.dismiss();
                //完成时候运行
            }
        });
    }

    //初始化控件
    public void initial(){


        rl_competition_content = (RelativeLayout)findViewById(R.id.rl_competition_content);
        iv_competition_back=(ImageView)findViewById(R.id.iv_competition_back);
        iv_share=(ImageView)findViewById(R.id.iv_share);
        tv_competition_group_booking_message = (TextView)findViewById(R.id.tv_competition_group_booking_message);//标题
        tv_game_time = (TextView)findViewById(R.id.tv_game_time);
        iv_locale=(ImageView)findViewById(R.id.iv_locale);






        tv_competition_announcer = (TextView)findViewById(R.id.tv_competition_announcer);
        tv_competition_type = (TextView)findViewById(R.id.tv_competition_type);
        tv_competition_time = (TextView)findViewById(R.id.tv_competition_time);
        tv_competition_place = (TextView)findViewById(R.id.tv_competition_place);
        tv_competition_announce_time =(TextView)findViewById(R.id.tv_competition_announce_time);
        tv_competition_note = (TextView)findViewById(R.id.tv_competition_note);
        rl_join=(RelativeLayout)findViewById(R.id.rl_join);
    }
    //数据实例
    public void dataInstance(String announcer, String type , String time , String place ,String registration_time, String node,String phone){
        tv_competition_announcer.setText(announcer);
        tv_competition_type.setText(type);
        tv_competition_time.setText(time);
        tv_competition_place.setText(place);
        tv_competition_announce_time.setText(registration_time);
        tv_competition_note.setText(node);
        this.phone=phone;
    }

    //控件点击事件
    public void onClick(View view){
        switch (view.getId()){

            //取消该界面并返回上一界面
            case R.id.iv_competition_back:
                Intent intent_cancel_one = new Intent(fragmenttwo_balls_details.this, MainActivity.class);
                startActivity(intent_cancel_one);
                this.finish();
                break;

            //分享
            case R.id.iv_share:
                Toast.makeText(fragmenttwo_balls_details.this, "分享", Toast.LENGTH_SHORT).show();
                //I/ntent intent_cancel_two = new Intent(fragmenttwo_balls_details.this, MainActivity.class);
                //startActivity(intent_cancel_two);
               // this.finish();
                break;

            //进入购物车界面
//            case R.id.iv_shopping_trolley:
//                break;

            //进行参团
//            case R.id.btn_competition_group_booking:
//                break;

            //赛事咨询
//            case R.id.ll_competition_advisory:
//                //checkPermission(fragmenttwo_balls_details.this);
//                if (TextUtils.isEmpty(phone)) {
//                    Toast.makeText(fragmenttwo_balls_details.this, "没有商家电话", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intentcall = new Intent();
//                    intentcall.setAction(Intent.ACTION_CALL);
//                    Uri data = Uri.parse("tel:" + phone);
//                    intentcall.setData(data);
//                    startActivity(intentcall);
//                    finish();
//                }
//                break;

            //个人报名
            case R.id.rl_join:

                Toast.makeText(fragmenttwo_balls_details.this, "参加报名", Toast.LENGTH_SHORT).show();
                try{
                    jsonObject=new JSONObject();
                    /*添加报名信息*/
                    Log.e("报名信息","phone:"+UserPublic.getUser()+"palce:"+tv_competition_place.getText()+"title:"+title);
                    jsonObject.put("userPhone", UserPublic.getUser());
                    jsonObject.put("userName","邱在杰");
                    jsonObject.put("gamePlace",tv_competition_place.getText().toString().substring(5,tv_competition_place.getText().length()));
                    jsonObject.put("gameName",title);
                    jsonObject.put("gameType",tv_competition_note.getText().toString().substring(5,tv_competition_note.getText().length()));
                }catch(Exception e){
                    e.printStackTrace();
                }
                refreshContent(Web_url.URL_AddJoinGame,jsonObject);


                //http(Web_url.URL_AddJoinGame,jsonObject); //网络请求
                Intent intent=new Intent(fragmenttwo_balls_details.this, Fragmenttwo_balls_commit.class);
                intent.putExtra("jsonData",jsonObject.toString());
                startActivity(intent);
                break;

            //组团报名
            case R.id.iv_locale:
                Toast.makeText(fragmenttwo_balls_details.this, "定位", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //系统按键返回上一界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(fragmenttwo_balls_details.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode,event);
    }
    private void checkPermission(Activity activity) {
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.CALL_PHONE,/*CALL_PHONE/*READ_EXTERNAL_STORAGE*/
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        try {
            //检测是否有拨号的权限
            int permission = ActivityCompat.checkSelfPermission(fragmenttwo_balls_details.this, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                // requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
                ActivityCompat.requestPermissions(fragmenttwo_balls_details.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void refreshContent(final String url,final JSONObject data) {
        refreshDialog = new RefreshDialog(fragmenttwo_balls_details.this,"正在提交...",R.mipmap.ic_dialog_loading);
        refreshDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    http(url,data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                (fragmenttwo_balls_details.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在之中可以终止线程
                    }
                });
            }
        }).start();
    }
}
