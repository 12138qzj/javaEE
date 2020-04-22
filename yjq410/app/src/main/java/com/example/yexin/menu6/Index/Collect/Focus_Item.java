package com.example.yexin.menu6.Index.Collect;

/**
 * Created by 23646 on 2020/3/26.
 */

public class Focus_Item {
    private int iv_focus;
    private String tv_focus_name;
    private String tv_focus_description;

    public Focus_Item(int iv_focus, String tv_focus_name, String tv_focus_description) {
        this.iv_focus = iv_focus;
        this.tv_focus_name = tv_focus_name;
        this.tv_focus_description = tv_focus_description;
    }

    public int getIv_focus() {
        return iv_focus;
    }

    public void setIv_focus(int iv_focus) {
        this.iv_focus = iv_focus;
    }

    public String getTv_focus_name() {
        return tv_focus_name;
    }

    public void setTv_focus_name(String tv_focus_name) {
        this.tv_focus_name = tv_focus_name;
    }

    public String getTv_focus_description() {
        return tv_focus_description;
    }

    public void setTv_focus_description(String tv_focus_description) {
        this.tv_focus_description = tv_focus_description;
    }

}
