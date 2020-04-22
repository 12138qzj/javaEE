package com.example.yexin.menu6.ChatModule.DataBaseOperation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yexin.menu6.ChatModule.Data.Chating;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/3/30.
 */

public class OperationChating {
    private SQLhelper db;

    /**
     * 聊天记录的存储
     * @param context
     */
    public OperationChating(Context context, String dbName){
        this.db=new SQLhelper(context,"Y"+dbName,null,1);
    }

    /**
     * 聊天记录的插入
     * @param a
     */
    public void Insert(Chating a){
        SQLiteDatabase insert=db.getWritableDatabase();
        insert.execSQL("insert into Chating (m_context,m_time,m_type,m_people,m_icon) values(?,?,?,?,?)",new Object[]{a.getContent(),a.getTime(),a.getType(),a.getName(),a.getIcon()});
        insert.close();
    }

    public void DataInsert(){

    }

    public void Delete(String account,String admin){
        SQLiteDatabase db_delete = db.getWritableDatabase();
        db_delete.execSQL("delete from Chatint where m_people=?",new String[]{admin+account});
    }

    public void Update(){

    }

    public LinkedList<Chating> select(String people, String admin){
        LinkedList<Chating> a=new LinkedList<>();
        int c=0;
        SQLiteDatabase db_Update = db.getReadableDatabase();
        Cursor cursor=db_Update.rawQuery("select * from Chating where m_people=? ",new String[]{admin+people});
        c=cursor.getCount();
        Log.e("在聊天查询数据",people+"和"+admin+"聊天记录有"+c);
        if(c>10){
            cursor=db_Update.rawQuery("select * from Chating where m_people=?",new String[]{admin+people});
            while(cursor.moveToNext()){
                Chating b=new Chating();
                b.setContent(cursor.getString(cursor.getColumnIndex("m_context")));
                b.setName(cursor.getString(cursor.getColumnIndex("m_people")));
                b.setTime(cursor.getString(cursor.getColumnIndex("m_time")));
                b.setType(cursor.getInt(cursor.getColumnIndex("m_type")));
                b.setIcon(cursor.getInt(cursor.getColumnIndex("m_icon")));
                Log.e("数据",cursor.getString(cursor.getColumnIndex("m_context"))+"  "+cursor.getString(cursor.getColumnIndex("m_people"))+"  "+cursor.getInt(cursor.getColumnIndex("m_icon")));
                a.add(b);
            }
            return a;
        }else{
            //where m_people=? or m_people=? order by id desc limit 0,"+c
            cursor=db_Update.rawQuery("select * from Chating where m_people=?",new String[]{admin+people});
            while(cursor.moveToNext()){
                Chating b=new Chating();
                b.setContent(cursor.getString(cursor.getColumnIndex("m_context")));
                b.setName(cursor.getString(cursor.getColumnIndex("m_people")));
                b.setTime(cursor.getString(cursor.getColumnIndex("m_time")));
                b.setType(cursor.getInt(cursor.getColumnIndex("m_type")));
                b.setIcon(cursor.getInt(cursor.getColumnIndex("m_icon")));
                Log.e("数据",cursor.getString(cursor.getColumnIndex("m_context"))+"  "+cursor.getString(cursor.getColumnIndex("m_people")));
                a.add(b);
            }
            return a;
        }

    }

//    public void CreateTable(String TableName){
//        Log.e("新好友创建数据库","数据库名"+TableName);
//        SQLiteDatabase dbCreate=db.getWritableDatabase();
//        dbCreate.execSQL("create table X"+TableName+" (id integer primary key autoincrement," +
//                "m_context varchar(100)," +
//                "m_time varchar(15)," +
//                "m_type integer," +
//                "m_people varchar(11))");
//        dbCreate.close();
//    }

    public boolean HaveData( String tablename){
        SQLiteDatabase db_=db.getReadableDatabase();
        Cursor cursor;
        boolean a=false;
        cursor = db_.rawQuery("select name from sqlite_master where type='table' ", null);
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
            if(name.equals(tablename))
            {
                return true;
            }
            Log.i("System.out", name);
        }
//        if(a)
//        {
//            cursor=db.query(tablename,null,null,null,null,null,null);
//            //检查是不是空表
//            if(cursor.getCount()>0)
//                return true;
//            else
//                return false;
//        }
//        else
        return false;

    }
}
