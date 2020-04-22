package com.example.yexin.menu6.ChatModule.Data;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/3/29.
 */

public class PrivateChatData {
    private String People;     //用户
    private String account;    //内容
    private String chat_num;   //未读消息数量
    private String isFriend;   //是否未朋友
    private String time;       //时间
    private String nickName;   //昵称
    private Boolean isChating; //是否在聊天
    private int IconHeader; //头像

    public PrivateChatData(String mpeople,String maccount,String mchatnum,String misfriend,String mtime,String mnickname,int micon){
        People=mpeople;
        account=maccount;
        chat_num=mchatnum;
        isFriend=misfriend;
        time=mtime;
        nickName=mnickname;
        IconHeader=micon;
    }

    public PrivateChatData(){

    }

    public PrivateChatData(String maccount,String nikcname,String mtime,int Icon){
        this.nickName=nikcname;
        account=maccount;
        time=mtime;
        IconHeader=Icon;
    }

    public String getPeople() {
        return People;
    }

    public void setPeople(String people) {
        People = people;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChat_num() {
        return chat_num;
    }

    public void setChat_num(String chat_num) {
        this.chat_num = chat_num;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getChating() {
        return isChating;
    }

    public void setChating(Boolean chating) {
        isChating = chating;
    }

    public int getIconHeader() {
        return IconHeader;
    }

    public void setIconHeader(int iconHeader) {
        IconHeader = iconHeader;
    }

}
