package com.example.yexin.menu6.ChatModule.DataBaseOperation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yexin.menu6.ChatModule.Data.Friends;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/3/30.
 */


public class OperationFriend {
    private SQLhelper db;
    /**
     * 好友本地列表,这里会创建数据库
     * @param context
     */
    public OperationFriend(Context context, String dbName){
        this.db=new SQLhelper(context,"Y"+dbName,null,1);
    }
    /**
     * 添加好友插入本地
     * @param a
     */
    public void Insert(Friends a){
        SQLiteDatabase insert=db.getWritableDatabase();
        insert.execSQL("insert into Friend (m_account,m_name,m_icon) values(?,?,?)",new Object[]{a.getAccount(),a.getName(),a.getIcon()});
        insert.close();
        Log.e("新好友插入","插入成功"+a.getAccount()+a.getName());
    }

    /**
     * 好友删除
     * @param a
     */
    public void Delete(Friends a){
        SQLiteDatabase Delete=db.getWritableDatabase();
        Delete.execSQL("Delete from Friend where m_account=?",new String[]{a.getAccount()});
        Delete.close();
    }

    public void Update(){
    }

    /**
     * 查找一个好友，信息的实体类
     * @param account
     * @return
     */
    public Friends find(String account){
        Friends friend=new Friends();
        SQLiteDatabase find=db.getReadableDatabase();
        Log.e("好友查询","要查询的数据"+account);
        Cursor cursor=find.rawQuery("select * from Friend where m_account=?",new String[]{account});
        Log.e("好友查询数据大小","数据大小为："+cursor.getCount());
        if(cursor.moveToFirst()==false){
            find.close();
            cursor.close();
            return null;
        }else{
            cursor.moveToFirst();
            Log.e("好友查询","好友查询到的数据"+cursor.getString(cursor.getColumnIndex("m_account")));
            friend.setAccount(cursor.getString(cursor.getColumnIndex("m_account")));
            friend.setName(cursor.getString(cursor.getColumnIndex("m_name")));
            friend.setIcon(cursor.getInt(cursor.getColumnIndex("m_icon")));
            find.close();
            cursor.close();
            return friend;
        }
    }

    public Boolean isFind(String account){
        SQLiteDatabase db_find = db.getReadableDatabase();
        Cursor cursor=db_find.rawQuery("select * from Friend where m_account=?",new String[]{account});
        int a=cursor.getCount();
        Log.e("find朋友内容","查到的数量"+a);
        if(a==0){
            Log.e("find朋友的内容","不存在这个好友，创建会话"+a);
            return true;
        }
        cursor.moveToFirst();
        Log.e("find的内容",cursor.getString(cursor.getColumnIndex("m_account"))+"存在这个好友，直接会话"+a);
        return false;
    }

    /**
     * 返回好友列表
     * @return
     */
    public LinkedList<Friends> Select(){
        LinkedList<Friends> friend=new LinkedList<>();
        SQLiteDatabase db_Update = db.getReadableDatabase();
        Cursor cursor=db_Update.query("Friend",null,null,null,null,null,null);//查询并获得游标
        while(cursor.moveToNext()){
            friend.add(new Friends(cursor.getString(cursor.getColumnIndex("m_name")),cursor.getString(cursor.getColumnIndex("m_account")),cursor.getInt(cursor.getColumnIndex("m_icon"))));
        }
        cursor.close();
        db_Update.close();
        return friend;
    }
}
