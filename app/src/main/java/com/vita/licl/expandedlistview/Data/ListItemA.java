package com.vita.licl.expandedlistview.Data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by pc on 2016/7/22.
 */
public class ListItemA extends BaseListItem{

    private String title;
    private String subtitle;
    private String content;
    private Drawable avatar;
    private String mainbgUrl;
    private Bitmap mainbg;

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getMainbg() {
        return mainbg;
    }

    public void setMainbg(Bitmap mainbg) {
        this.mainbg = mainbg;
    }

    public String getMainbgUrl() {
        return mainbgUrl;
    }

    public void setMainbgUrl(String mainbgUrl) {
        this.mainbgUrl = mainbgUrl;
    }
}
