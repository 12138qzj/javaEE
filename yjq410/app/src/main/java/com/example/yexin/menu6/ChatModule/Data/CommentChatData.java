package com.example.yexin.menu6.ChatModule.Data;

/**
 * Created by DELL on 2020/3/29.
 */

public class CommentChatData {
    private String content;
    private String people;
    private String time;
    private String header;
    private String ball;

    public CommentChatData(String mcontent,String mpeople,String mtime){
        this.content=mcontent;
        this.people=mpeople;
        this.time=mtime;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }
}
