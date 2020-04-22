package com.example.yexin.menu6.ChatModule.Data;

import android.content.Intent;

/**
 * Created by DELL on 2020/3/30.
 */

public class Friends {
    private String name;     //姓名
    private String account; //账号
    private int Icon;    //头像

    public Friends(){

    }

    public Friends(String mname,String maccound,int mIcon){
        name=mname;
        account=maccound;
        Icon=mIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }
}
