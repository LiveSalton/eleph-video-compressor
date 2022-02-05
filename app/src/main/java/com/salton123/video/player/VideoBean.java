package com.salton123.video.player;

import android.net.Uri;

/**
 * User: newSalton@outlook.com
 * Date: 2018/2/26 下午6:02
 * ModifyTime: 下午6:02
 * Description:
 */
public class VideoBean {
    public int resId;
    public Uri uri;

    public VideoBean() {
    }

    public VideoBean(int resId, String url) {
        this.resId = resId;
        uri = Uri.parse(url);
    }

    public VideoBean(int resId, Uri uri) {
        this.resId = resId;
        this.uri = uri;
    }

}
