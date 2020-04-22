package com.example.yexin.menu6.ChatModule;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.example.yexin.menu6.R;

import static com.example.yexin.menu6.ChatModule.Data.StaticData.CHANEL_ID_INFO;
import static com.example.yexin.menu6.ChatModule.Data.StaticData.CHANEL_ID_LEAVECHAT;
import static com.example.yexin.menu6.ChatModule.Data.StaticData.CHANEL_ID_PUSH;
import static com.example.yexin.menu6.ChatModule.Data.StaticData.CHANEL_NAME_INFO;
import static com.example.yexin.menu6.ChatModule.Data.StaticData.CHANEL_NAME_LEAVECHAT;
import static com.example.yexin.menu6.ChatModule.Data.StaticData.CHANEL_NAME_PUSH;

/**
 * Created by DELL on 2020/3/13.
 */

public class Notification {
    /**
     *
     * @param context
     * @param chatFor
     */
        @TargetApi(Build.VERSION_CODES.O)
        public static  void  chatNotification(Context context, int number_chat, int number_people, int chatFor) {
            NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder;
            //获取Notification实例   获取Notification实例有很多方法处理    在此我只展示通用的方法（虽然这种方式是属于api16以上，但是已经可以了，毕竟16以下的Android机很少了，如果非要全面兼容可以用）
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                //向上兼容 用Notification.Builder构造notification对象
                //创建 通知通道  channelid和channelname是必须的（自己命名就好）聊天通道
//              channel_push=new NotificationChannel(CHANEL_ID_TS,CHANEL_NAME_TS,NotificationManager.IMPORTANCE_DEFAULT);
                NotificationChannel channel_chat = new NotificationChannel(CHANEL_ID_INFO, CHANEL_NAME_INFO, NotificationManager.IMPORTANCE_MAX);
                channel_chat.enableLights(true);//是否在桌面icon右上角展示小红点
                channel_chat.setLightColor(Color.GREEN);//小红点颜色
                channel_chat.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
                mBuilder= new NotificationCompat.Builder(context);
                mBuilder .setContentTitle("好友消息通知")
                        .setContentText(number_people+"联系人发来"+number_chat+"消息")
                        .setTicker("好友消息")
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setColor(Color.parseColor("#FEDA26"));
                notificationManager.createNotificationChannel(channel_chat);
            }else {
                //向下兼容 用NotificationCompat.Builder构造notification对象
                mBuilder = new NotificationCompat.Builder(context);
                mBuilder .setContentTitle("好友消息通知")
                        .setContentText(number_people+"联系人发来"+number_chat+"消息")
                        .setTicker("好友消息")
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setColor(Color.parseColor("#FEDA26"));
            }
            notificationManager.notify(chatFor,mBuilder.build());
        }


    @TargetApi(Build.VERSION_CODES.O)
    public static void pushNotification(Context context, String Title, String Text, String Ticker){
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder;
        //获取Notification实例   获取Notification实例有很多方法处理    在此我只展示通用的方法（虽然这种方式是属于api16以上，但是已经可以了，毕竟16以下的Android机很少了，如果非要全面兼容可以用）
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            //向上兼容 用Notification.Builder构造notification对象
            //创建 通知通道  channelid和channelname是必须的（自己命名就好）推送通道
//              channel_push=new NotificationChannel(CHANEL_ID_TS,CHANEL_NAME_TS,NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel_push = new NotificationChannel(CHANEL_ID_PUSH, CHANEL_NAME_PUSH, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder= new NotificationCompat.Builder(context);
            mBuilder .setContentTitle(Title)
                    .setContentText(Text)
                    .setTicker(Ticker)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setColor(Color.parseColor("#FEDA26"));
            notificationManager.createNotificationChannel(channel_push);
        }else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder .setContentTitle(Title)
                    .setContentText(Text)
                    .setTicker(Ticker)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setColor(Color.parseColor("#FEDA26"));
        }
        notificationManager.notify(3321,mBuilder.build());
    }

    //留言通道
    @TargetApi(Build.VERSION_CODES.O)
    public static void LeaveChating(Context context, int number_people, int chatFor){
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder;
        //获取Notification实例   获取Notification实例有很多方法处理    在此我只展示通用的方法（虽然这种方式是属于api16以上，但是已经可以了，毕竟16以下的Android机很少了，如果非要全面兼容可以用）
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            //向上兼容 用Notification.Builder构造notification对象
            //创建 聊天通道
//              channel_push=new NotificationChannel(CHANEL_ID_TS,CHANEL_NAME_TS,NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel_chat = new NotificationChannel(CHANEL_ID_LEAVECHAT, CHANEL_NAME_LEAVECHAT, NotificationManager.IMPORTANCE_MAX);
            channel_chat.enableLights(true);//是否在桌面icon右上角展示小红点
            channel_chat.setLightColor(Color.GREEN);//小红点颜色
            channel_chat.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            mBuilder= new NotificationCompat.Builder(context);
            mBuilder .setContentTitle("留言消息")
                    .setContentText(number_people+"人给你留言")
                    .setTicker("留言")
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setColor(Color.parseColor("#FEDA26"));
            notificationManager.createNotificationChannel(channel_chat);
        }else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder .setContentTitle("留言消息")
                    .setContentText(number_people+"个朋友给你留言")
                    .setTicker("留言")
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setColor(Color.parseColor("#FEDA26"));
        }
        notificationManager.notify(chatFor,mBuilder.build());
    }
}
