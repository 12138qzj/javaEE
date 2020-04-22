package com.example.yexin.menu6.Common.Public;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.yexin.menu6.Common.App.AppData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DELL on 2019/10/13.
 */

public class User_public {

//    private   SharedPreferences preferences=null;
//    private   Context context;
//    private   String User=null;  //用户账号
//    private   boolean User_flag=false;  //用户的现状态
//    private   String User_str=null; //用户的长连接
//    private   boolean first=false;  //是否取登入界面还是主页面
//    private   boolean isFirst=false; //是否第一次登入
//    private   String Icon=null; //用户头像地址
//    private   String Icon_time=null; //用户的头像标识
//    private   String User_n=null;  //用户的昵称
//    private   String User_q=null; //用户的签名
//    private   String User_level=null; //用户的等级
//    private   String User_sex=null;  //用户性别
//    private   String User_datetime=null; //用户的生日
//    private   String User_place=null;  //用户的地区

    private boolean isWifi=false;
    private String User=null;           //用户账号
    private boolean User_flag=false;   //用户的现状态
    private String User_str=null;       //用户的长连接
    //    private   static boolean firsT=false;       //是否去登入界面还是主页面
//    private   static boolean isFirst=false;     //是否第一次登入
    private String Icon=null;           //用户头像地址
    private String Icon_time=null;      //用户的头像标识
    private String User_n=null;         //用户的昵称
    private String User_q=null;         //用户的签名
    private String User_level=null;     //用户的等级
    private String User_sex=null;       //用户的性别
    private String User_datetime=null;  //用户的生日
    private String User_place=null;     //用户的地区
    private Context context;
    private SharedPreferences sharedPreferences;



    /**

     * 初始化问题
     * @param mContext

     */
    public User_public(Context mContext){
        this.context=mContext;
        this.sharedPreferences=context.getSharedPreferences(AppData.UserInfoShare,MODE_PRIVATE);
    }

    public User_public(){
    }

    public void CreateShareFil(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("User_flag",false);  //用户状态
        editor.putString("User",null);         //用户账号
        editor.putString("Icon",null);         //用户头像地址 暂时不用
        editor.putString("User_str",null) ;   //用户的长连接
//        editor.putString("Icon_time",null);   //用户头像的有效暂时不用
        editor.putString("User_n",null);      //用户的昵称
        editor.putString("User_q",null);      //用户的签名
        editor.putString("User_level",null); //用户等级
        editor.putString("User_sex",null);   //用户性别
        editor.putString("User_datetime",null);  //用户生日
        editor.putString("User_place",null);     //用户的地区
        editor.putString("Icon_time",null);    //头像标识
        editor.putString("User_password",null);
        editor.commit();
    }

    public void getShareFileContent(){
        //SharedPreferences.Editor editor=sharedPreferences.edit();
        UserPublic.setUser(sharedPreferences.getString("User",null));                        //用户账号
        UserPublic.setUser_flag(sharedPreferences.getBoolean("User_flag",false));           //用户的现状态
        UserPublic.setUser_str(sharedPreferences.getString("User_str",null));               //用户的长连接
        UserPublic.setIcon(sharedPreferences.getString("Icon",null));                       //用户头像地址
        //String Icon_time=preferences.getString("User",null);;                        //用户的头像标识
        UserPublic.setUser_n(sharedPreferences.getString("User_n",null));                  //用户的昵称
        UserPublic.setUser_q(sharedPreferences.getString("User_q",null));                  //用户的签名
        UserPublic.setUser_level(sharedPreferences.getString("User_level",null));         //用户的等级
        UserPublic.setUser_sex(sharedPreferences.getString("User_sex",null));             //用户的性别
        UserPublic.setUser_datetime(sharedPreferences.getString("User_datetime",null));  //用户的生日
        UserPublic.setUser_place(sharedPreferences.getString("User_place",null));         //用户的地区
        UserPublic.setUser_password(sharedPreferences.getString("User_password",null));
//        User=preferences.getString("User",null);  //个人账号
//        User_flag=preferences.getBoolean("User_flag",false); //登入状态
//        User_str=preferences.getString("User_str",null); //长连接
//        UserPublic.setUser(User);
//        UserPublic.setUser_flag(User_flag);
//        UserPublic.setUser_str(User_str);
//        Log.e("adgadg","用户"+ UserPublic.getUser()+"状态"+UserPublic.isUser_flag()+"长连接"+UserPublic.getUser_str());
    }

    public void getShareFileShort(){
        User=sharedPreferences.getString("User",null);               //个人账号
        User_flag=sharedPreferences.getBoolean("User_flag",false); //登入状态
        User_str=sharedPreferences.getString("User_str",null);     //长连接
        UserPublic.setUser(User);
        UserPublic.setUser_flag(User_flag);
        UserPublic.setUser_str(User_str);
        Log.e("Share获取用户信息","用户"+ UserPublic.getUser()+"状态"+UserPublic.isUser_flag()+"长连接"+UserPublic.getUser_str());
    }

    public boolean isWifi() {
        return isWifi;
    }

    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public String getUser() {
        User=UserPublic.getUser();
        return User;
    }

    public void setUser(String user) {
        User = user;
        UserPublic.setUser(User);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User",User);
        editor.commit();
    }

    public boolean isUser_flag() {
        User_flag=UserPublic.isUser_flag();
        return User_flag;
    }

    public void setUser_flag(boolean user_flag) {
        User_flag = user_flag;
        UserPublic.setUser_flag(User_flag);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("User_flag",User_flag);
        editor.commit();
    }

    public String getUser_str() {
        User_str=UserPublic.getUser_str();
        return User_str;
    }

    public void setUser_str(String user_str) {
        User_str = user_str;
        UserPublic.setUser_str(User_str);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_str",User_str);
        editor.commit();
    }

    public String getIcon() {
        Icon=UserPublic.getIcon();
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
        UserPublic.setIcon(Icon);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Icon",Icon);
        editor.commit();
    }

    public String getIcon_time() {
        Icon_time=UserPublic.getIcon_time();
        return Icon_time;
    }

    public void setIcon_time(String icon_time) {
        Icon_time = icon_time;
        UserPublic.setIcon_time(Icon_time);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Icon_time",Icon_time);
        editor.commit();
    }

    public String getUser_n() {
        User_n=UserPublic.getUser_n();
        return User_n;
    }

    public void setUser_n(String user_n) {
        User_n = user_n;
        UserPublic.setUser_n(User_n);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_n",User_n);
        editor.commit();
    }

    public String getUser_q() {
        User_q=UserPublic.getUser_q();
        return User_q;
    }

    public void setUser_q(String user_q) {
        User_q = user_q;
        UserPublic.setUser_q(User_q);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_q",User_q);
        editor.commit();
    }

    public String getUser_level() {
        User_level=UserPublic.getUser_level();
        return User_level;
    }

    public void setUser_level(String user_level) {
        User_level = user_level;
        UserPublic.setUser_level(User_level);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_level",User_level);
        editor.commit();
    }

    public String getUser_sex() {
        User_sex=UserPublic.getUser_sex();
        return User_sex;
    }

    public void setUser_sex(String user_sex) {
        User_sex = user_sex;
        UserPublic.setUser_sex(User_sex);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_sex",User_sex);
        editor.commit();
    }

    public String getUser_datetime() {
        User_datetime=UserPublic.getUser_datetime();
        return User_datetime;
    }

    public void setUser_datetime(String user_datetime) {
        User_datetime = user_datetime;
        UserPublic.setUser_datetime(User_datetime);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_datetime",User_datetime);
        editor.commit();
    }

    public String getUser_place() {
        User_place=UserPublic.getUser_place();
        return User_place;
    }

    public void setUser_place(String user_place) {
        User_place = user_place;
        UserPublic.setUser_place(User_place);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_place",User_place);
        editor.commit();
    }

    public void setPassword(String a){
        User_place = a;
        UserPublic.setUser_password(User_place);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User_password",User_place);
        editor.commit();
    }

    public String getPassword(){
        String psd=UserPublic.getUser_password();
        return psd;
    }
}
