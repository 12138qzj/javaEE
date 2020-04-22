package com.example.yexin.menu6.Common.Public;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.yexin.menu6.Common.App.AppData;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DELL on 2020/3/28.
 */

public class ChackOperation {
    private Context context;

    public ChackOperation(Context mcontext){
        context=mcontext;
    }

    public boolean getCheckingResult(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(AppData.IfCheckLogin,MODE_PRIVATE);
        return sharedPreferences.getBoolean("isChecking",false);
    }

    public void ChangeChecking(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(AppData.IfCheckLogin,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(sharedPreferences.getBoolean("isChecking",false)){
            editor.putBoolean("isChecking",false);
            editor.commit();
        }else{
            editor.putBoolean("isChecking",true);
            editor.commit();
        }
    }

    public void setCheckingTrue(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(AppData.IfCheckLogin,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isChecking",true);
        editor.commit();
    }

    public void setCheckingFalse(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(AppData.IfCheckLogin,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isChecking",false);
        editor.commit();
    }
}
