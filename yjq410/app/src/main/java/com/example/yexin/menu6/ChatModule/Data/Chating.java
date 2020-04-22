package com.example.yexin.menu6.ChatModule.Data;

import java.io.File;

/**
 * Created by DELL on 2020/3/9.
 */

public class Chating {
    public final static int CHAT_SENDER_OTHER= 0;
    public final static int CHAT_SENDER_ME = 1;

    public final static int CHAT_RECEIVE_TEXT = 11;
    public final static int CHAT_RECEIVEE_IMG = 12;

    public final static int CHAT_SEND_TEXT=13;
    public final static int CHAT_SEND_IMG=14;

    private String id;
    private int msgType;
    private String image;
    private int Type;
    private String time;  //时间
    private String name;   //姓名
    private String content;  //内容
    private int icon;

    private File file;
    private int fileLoadstye;

    public Chating(){

    }

    public Chating(String mcontent, String mtime, int mtype, String mname,int micon){
        this.content=mcontent;
        this.time=mtime;
        this.Type=mtype;
        this.name=mname;
        this.icon=micon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getFileLoadstye() {
        return fileLoadstye;
    }

    public void setFileLoadstye(int fileLoadstye) {
        this.fileLoadstye = fileLoadstye;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
