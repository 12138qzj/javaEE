package com.example.yexin.menu6.ChatModule.DataBaseOperation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DELL on 2020/3/14.
 */

public class SQLhelper extends SQLiteOpenHelper {
    /**
     *
     * @param context
     * @param name 数据库名
     * @param factory null
     * @param version 第一次是一
     */
    public SQLhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.e("SQLhelper","创建的数据库名"+name+"版本"+version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库创建
        /**
         * 账号
         * 姓名
         */
        db.execSQL("create table Friend (m_account varchar(11) primary key," +
                "m_name varchar(10)," +
                "m_icon integer)");
        /**
         * 主键
         * 内容
         * 数量
         * 是否我朋友
         * 账号
         * 时间
         * 昵称
         */
        db.execSQL("create table Chat(id integer primary key autoincrement," +
                "m_account varchar(50)," +
                "m_num varchar(10)," +
                "m_isFriend varchar(1)," +
                "m_people varchar(11)," +
                "m_time varchar(10)," +
                "m_nickName varchar(20)," +
                "m_icon integer)");
        /**
         * 主键
         * 内容
         * 时间
         * 类型
         * 账号 是两个人的账号拼凑起来，前面是本人，后面是聊天的人
         * 头像
         */
        db.execSQL("create table Chating (id integer primary key autoincrement," +
                "m_context varchar(100)," +
                "m_time varchar(15)," +
                "m_type integer," +
                "m_people varchar(30)," +
                "m_icon integer)");
        Log.e("创建数据库","创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库升级
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
