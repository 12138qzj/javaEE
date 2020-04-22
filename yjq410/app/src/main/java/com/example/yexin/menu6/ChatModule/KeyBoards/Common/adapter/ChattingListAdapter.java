package com.example.yexin.menu6.ChatModule.KeyBoards.Common.adapter;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yexin.menu6.ChatModule.Data.Chating;
import com.example.yexin.menu6.ChatModule.KeyBoards.Common.util.ImageLoadUtils;
import com.example.yexin.menu6.ChatModule.KeyBoards.SimpleCommonUtils;
import com.example.yexin.menu6.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sj.keyboard.utils.imageloader.ImageBase;

public class ChattingListAdapter extends BaseAdapter {

    private final int VIEW_TYPE_COUNT = 8;
    private final int VIEW_TYPE_LEFT_TEXT = 0;
    private final int VIEW_TYPE_LEFT_IMAGE = 1;
    private final int VIEW_TYPE_RIGHT_TEXT=2;
    private final int VIEW_TYPE_RIGHT_IMAGE=3;

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<Chating> mData;

    public ChattingListAdapter(Activity activity) {
        this.mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    public void addData(List<Chating> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        for (Chating bean : data) {
            addData(bean, false, false);
        }
        this.notifyDataSetChanged();
    }

    public void addData(Chating bean, boolean isNotifyDataSetChanged, boolean isFromHead) {
        if (bean == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }

        if (bean.getMsgType() <= 0) {
            String content = bean.getContent();
            if (content != null) {
                if (content.indexOf("[img]") >= 0) {
                    bean.setImage(content.replace("[img]", ""));
                    if(bean.getType()==1){
                        bean.setMsgType(Chating.CHAT_RECEIVEE_IMG);
                    }else{
                        bean.setMsgType(Chating.CHAT_SEND_IMG);
                    }
                } else {
                    if(bean.getType()==1){
                        bean.setMsgType(Chating.CHAT_RECEIVE_TEXT);
                    }else{
                        bean.setMsgType(Chating.CHAT_SEND_TEXT);
                    }
                    }
            }
        }

        if (isFromHead) {
            mData.add(0, bean);
        } else {
            mData.add(bean);
        }

        if (isNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) == null) {
            return -1;
        }
        if(mData.get(position).getMsgType()==Chating.CHAT_RECEIVE_TEXT){
            return VIEW_TYPE_LEFT_TEXT;
        }else if(mData.get(position).getMsgType()==Chating.CHAT_RECEIVEE_IMG){
            return VIEW_TYPE_LEFT_IMAGE;
        }else if(mData.get(position).getMsgType()==Chating.CHAT_SEND_TEXT){
            return VIEW_TYPE_RIGHT_TEXT;
        }else{
            return VIEW_TYPE_RIGHT_IMAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Chating bean = mData.get(position);
        int type = getItemViewType(position);
        int chatType=mData.get(position).getType();
        View holderView = null;
        switch (type) {
            case VIEW_TYPE_LEFT_TEXT:
                ViewHolderLeftTextReceive holder;
                if (convertView == null) {
                    holder = new ViewHolderLeftTextReceive();
                    holderView = mInflater.inflate(R.layout.jmui_chat_item_receive_text, null);
                    holderView.setFocusable(true);
                    holder.iv_avatar = (ImageView) holderView.findViewById(R.id.jmui_avatar_iv);
                    holder.tv_content = (TextView) holderView.findViewById(R.id.jmui_msg_content);
                    holderView.setTag(holder);
                    convertView = holderView;
                } else {
                    holder = (ViewHolderLeftTextReceive) convertView.getTag();
                }
                holder.iv_avatar.setImageResource(mData.get(position).getIcon());
                disPlayLeftTextView(position, convertView, holder, bean);
                break;
            case VIEW_TYPE_LEFT_IMAGE:
                ViewHolderLeftImageReceive imageHolder;
                if (convertView == null) {
                    imageHolder = new ViewHolderLeftImageReceive();
                    holderView = mInflater.inflate(R.layout.listitem_chat_left_image, null);
                    holderView.setFocusable(true);
                    imageHolder.iv_avatar = (ImageView) holderView.findViewById(R.id.iv_avatar_receive);
                    imageHolder.iv_image = (ImageView) holderView.findViewById(R.id.iv_image_receive);
                    holderView.setTag(imageHolder);
                    convertView = holderView;
                } else {
                    imageHolder = (ViewHolderLeftImageReceive) convertView.getTag();
                }
                imageHolder.iv_avatar.setImageResource(mData.get(position).getIcon());
                disPlayLeftImageView(position, convertView, imageHolder, bean);
                break;
            case VIEW_TYPE_RIGHT_IMAGE:
                ViewHolderLeftImageReceiveSend imageHolderSend;
                if (convertView == null) {
                    imageHolderSend = new ViewHolderLeftImageReceiveSend();
                    holderView = mInflater.inflate(R.layout.listitem_chat_right_image, null);
                    holderView.setFocusable(true);
                    imageHolderSend.iv_avatar = (ImageView) holderView.findViewById(R.id.iv_avatar_send);
                    imageHolderSend.iv_image = (ImageView) holderView.findViewById(R.id.iv_image_send);
                    holderView.setTag(imageHolderSend);
                    convertView = holderView;
                } else {
                    imageHolderSend = (ViewHolderLeftImageReceiveSend) convertView.getTag();
                }
                disPlayRightImageView(position, convertView, imageHolderSend, bean);
                break;
            case VIEW_TYPE_RIGHT_TEXT:
                ViewHolderLeftTextReceiveSend holderSend;
                if (convertView == null) {
                    holderSend = new ViewHolderLeftTextReceiveSend();
                    holderView = mInflater.inflate(R.layout.jmui_chat_item_send_text, null);
                    holderView.setFocusable(true);
                    holderSend.iv_avatar = (ImageView) holderView.findViewById(R.id.jmui_receive);
                    holderSend.tv_content = (TextView) holderView.findViewById(R.id.text_receipt);
                    holderView.setTag(holderSend);
                    convertView = holderView;
                } else {
                    holderSend = (ViewHolderLeftTextReceiveSend) convertView.getTag();
                }
                disPlayRightTextView(position, convertView, holderSend, bean);
                break;
            default:
                convertView = new View(mActivity);
                break;
        }
        return convertView;
    }

    public void disPlayRightTextView(int position, View view, ViewHolderLeftTextReceiveSend holder, Chating bean) {
        setContent(holder.iv_avatar,holder.tv_content, bean.getContent(),bean.getIcon());
    }


    public void disPlayLeftTextView(int position, View view, ViewHolderLeftTextReceive holder, Chating bean) {
        setContent(holder.iv_avatar,holder.tv_content, bean.getContent(),bean.getIcon());
    }

    public void setContent(ImageView header,TextView tv_content, String content,int icon) {
        SimpleCommonUtils.spannableEmoticonFilter(header,tv_content, content,icon);
    }

    public void disPlayRightImageView(int position, View view, ViewHolderLeftImageReceiveSend holder, Chating bean) {
        try {
            if (ImageBase.Scheme.FILE == ImageBase.Scheme.ofUri(bean.getImage())) {
                String filePath = ImageBase.Scheme.FILE.crop(bean.getImage());
                Glide.with(holder.iv_image.getContext())
                        .load(filePath)
                        .into(holder.iv_image);
            } else {
                ImageLoadUtils.getInstance(mActivity).displayImage(bean.getImage(), holder.iv_image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void disPlayLeftImageView(int position, View view, ViewHolderLeftImageReceive holder, Chating bean) {
        holder.iv_avatar.setImageResource(bean.getIcon());
        try {
            if (ImageBase.Scheme.FILE == ImageBase.Scheme.ofUri(bean.getImage())) {
                String filePath = ImageBase.Scheme.FILE.crop(bean.getImage());
                Glide.with(holder.iv_image.getContext())
                        .load(filePath)
                        .into(holder.iv_image);
            } else {
                ImageLoadUtils.getInstance(mActivity).displayImage(bean.getImage(), holder.iv_image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final class ViewHolderLeftTextReceive {
        public ImageView iv_avatar;
        public TextView tv_content;

    }

    public final class ViewHolderLeftImageReceive {
        public ImageView iv_avatar;
        public ImageView iv_image;

    }

    public final class ViewHolderLeftTextReceiveSend {
        public ImageView iv_avatar;
        public TextView tv_content;

    }

    public final class ViewHolderLeftImageReceiveSend {
        public ImageView iv_avatar;
        public ImageView iv_image;

    }
}