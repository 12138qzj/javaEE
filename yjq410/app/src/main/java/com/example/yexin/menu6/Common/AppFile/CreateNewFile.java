package com.example.yexin.menu6.Common.AppFile;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.yexin.menu6.Common.App.StorageCache;

import java.io.File;

/**
 * Created by DELL on 2020/3/28.
 */

public class CreateNewFile {


    public CreateNewFile(Context context){
        String sdPath = Environment.getExternalStorageDirectory().getPath();
            String UserinfoPath;
            try{
                File file=new File(sdPath+"/yjq");
                if (!file.exists()) {
                    file.mkdirs();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            Log.e("qew",sdPath);
            try{
                File file=new File(StorageCache.UserCache(context));
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}
