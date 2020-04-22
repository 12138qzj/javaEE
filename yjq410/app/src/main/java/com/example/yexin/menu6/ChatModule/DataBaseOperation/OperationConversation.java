package com.example.yexin.menu6.ChatModule.DataBaseOperation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yexin.menu6.ChatModule.Data.PrivateChatData;

import java.util.LinkedList;

/**
 * Created by DELL on 2020/3/30.
 */

public class OperationConversation {
    private SQLhelper db;

    /**
     * 聊天会话的存储
     * @param context
     */
    public OperationConversation(Context context, String dbName){
        this.db=new SQLhelper(context,"Y"+dbName,null,1);
    }

    /**
     * 在有新会话添加的基础上，新增会话
     * @param a
     */
    public void Insert(PrivateChatData a){
        Log.e("插入会话",a.getAccount());
        SQLiteDatabase insert=db.getWritableDatabase();
        insert.execSQL("insert into Chat (m_account,m_num,m_isFriend,m_people,m_time,m_nickName,m_icon) values(?,?,?,?,?,?,?)",new Object[]{a.getAccount(),a.getChat_num(),a.getIsFriend(),a.getPeople(),a.getTime(),a.getNickName(),a.getIconHeader()});
        insert.close();
    }

    /**
     * 删除一个会话，根据一个用户的ID
     * @param a
     */
    public void Delete(String a){
        Log.e("删除会话",a);
        SQLiteDatabase delete=db.getWritableDatabase();
        delete.execSQL("delete from Chat where m_people=?",new String[]{a});
        delete.close();
    }

    /**
     * 接收新消息，在已经有会话的基础上
     * 把这个会话重新放在首位
     * @param a
     */
    public void Receive(PrivateChatData a){
        Log.e("更新数据",a.getAccount());
        SQLiteDatabase receive=db.getWritableDatabase();
        receive.execSQL("delete from Chat where m_people=?",new String[]{a.getPeople()});
        receive.execSQL("insert into Chat (m_account,m_num,m_isFriend,m_people,m_time,m_nickName,m_icon) values(?,?,?,?,?,?,?)",new Object[]{a.getAccount(),a.getChat_num(),a.getIsFriend(),a.getPeople(),a.getTime(),a.getNickName(),a.getIconHeader()});
        receive.close();
    }

    /**
     * 删除所有的会话
     */
    public void DeleteAll(){
        Log.e("删除所有会话","删除成功");
        SQLiteDatabase deleteAll=db.getWritableDatabase();
        deleteAll.execSQL("delete from Chat");
        deleteAll.close();
    }

    /**
     * 在已有的会话的基础上，如有新消息，更改，更改内容，和消息数量
     * @param context
     * @param time
     */
    public void Update(String context,String time,String num,String people_,String icon){
        SQLiteDatabase db_update = db.getReadableDatabase();
        db_update.execSQL("update Chat set m_account=?,m_time=?,m_num=?,m_icon=? where m_people=?",new String[]{context,time,num,people_,icon});
        db_update.close();
    }

    /**
     * 在已有的会话基础上，点击查看消息，清除未读消息数量
     * @param num
     */
    public void UpdateNum(String num){
        SQLiteDatabase db_update = db.getReadableDatabase();
        db_update.execSQL("update Chat set m_num=?",new String[]{num});
        db_update.close();
    }

    /**
     * 查找会话
     * @param a
     * @return
     */
    public Boolean find(String a){
        Log.e("查询会话","会话是不是存在");
        SQLiteDatabase db_find = db.getReadableDatabase();
        Cursor cursor=db_find.rawQuery("select * from Chat where m_people=?",new String[]{a});
        int number=cursor.getCount();
        Log.e("find的数量",number+"");
        if(number==0){
            Log.e("find的内容","会话不存在");
            return true;
        }
        cursor.moveToFirst();
        Log.e("find的内容",cursor.getString(cursor.getColumnIndex("m_account")));
        return false;
    }

    /**
     * 加载会话
     * @return
     */
    public LinkedList<PrivateChatData> select(){
        LinkedList<PrivateChatData> ChatAll=new LinkedList<>();
        PrivateChatData data=new PrivateChatData();
        SQLiteDatabase db_Update = db.getReadableDatabase();
        Cursor cursor=db_Update.rawQuery("select * from Chat order by id desc",null);//查询并获得游标
        while(cursor.moveToNext()){
            String account=cursor.getString(cursor.getColumnIndex("m_account"));
            if(account.indexOf("[img]") >= 0){
                account="[动态表情]";
            }
            data.setAccount(account);
            data.setChat_num(cursor.getString(cursor.getColumnIndex("m_num")));
            data.setIsFriend(cursor.getString(cursor.getColumnIndex("m_isFriend")));
            data.setPeople(cursor.getString(cursor.getColumnIndex("m_people")));
            data.setTime(cursor.getString(cursor.getColumnIndex("m_time")));
            data.setNickName(cursor.getString(cursor.getColumnIndex("m_nickName")));
            data.setIconHeader(cursor.getInt(cursor.getColumnIndex("m_icon")));
            ChatAll.add(data);
            Log.e("添加",cursor.getString(cursor.getColumnIndex("m_people")));
        }
        db_Update.close();
        cursor.close();
        Log.e("获取本地会话","会话数量"+ChatAll.size());
        return ChatAll;
    }

    /**
     * 将现有的会话添加入库
     * @param b
     */
    public void InsertAll(LinkedList<PrivateChatData> b){
        SQLiteDatabase insertall=db.getWritableDatabase();
        PrivateChatData a;
        for(int i=0;i<b.size();i++){
            a=b.get(i);
            insertall.execSQL("insert into Chat (m_account,m_num,m_isFriend,m_people,m_time,m_nickName,m_icon) values(?,?,?,?,?,?,?)",new Object[]{a.getAccount(),a.getChat_num(),a.getIsFriend(),a.getPeople(),a.getTime(),a.getNickName(),a.getIconHeader()});
            Log.e("插入",a.getAccount());
        }
        insertall.close();
    }
}
