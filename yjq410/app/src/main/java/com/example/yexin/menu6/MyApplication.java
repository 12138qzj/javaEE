package com.example.yexin.menu6;

import android.app.ActivityManager;
import android.app.Application;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;

//import com.baidu.mapapi.CoordType;
//import com.baidu.mapapi.SDKInitializer;
import com.example.yexin.menu6.Common.Wifi.NetManagerService;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.xutils.x;

import java.util.Iterator;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DELL on 2019/9/29.
 */

public class MyApplication extends Application {
    private NetManagerService netManagerService;
    private IntentFilter intentFilter;
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Log.e("Application","启动程序");
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e("yjq", "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);


        x.Ext.init(this);
        x.Ext.setDebug(true); //是否输出debug日志，开启debug会影响性能。
        /**
         * 注册广播监听器
         */
        netManagerService=new NetManagerService();
        intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netManagerService, intentFilter);

//        SDKInitializer.initialize(this);
//        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);
        //设定中心点坐标

    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
        unregisterReceiver(netManagerService);
    }
    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        super.onTrimMemory(level);
    }
}
