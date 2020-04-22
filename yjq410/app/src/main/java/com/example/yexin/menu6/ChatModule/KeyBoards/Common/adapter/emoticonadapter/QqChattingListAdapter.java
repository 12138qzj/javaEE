package com.example.yexin.menu6.ChatModule.KeyBoards.Common.adapter.emoticonadapter;

import android.app.Activity;
import android.widget.TextView;

import com.example.yexin.menu6.ChatModule.KeyBoards.Common.adapter.ChattingListAdapter;
import com.example.yexin.menu6.ChatModule.KeyBoards.SimpleCommonUtils;


public class QqChattingListAdapter extends ChattingListAdapter {

    public QqChattingListAdapter(Activity activity) {
        super(activity);
    }

    public void setContent(TextView tv_content, String content) {
        SimpleCommonUtils.spannableEmoticonFilter(tv_content, content);
    }
}