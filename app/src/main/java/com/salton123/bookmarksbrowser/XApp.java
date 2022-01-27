package com.salton123.bookmarksbrowser;

import android.app.Application;

import org.xutils.x;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 16:11
 * ModifyTime: 16:11
 * Description:
 */
public class XApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        // Picasso.setSingletonInstance(
        //     new Picasso.Builder(this)
        //         .addRequestHandler(new VideoRequestHandler())
        //         .build()
        // );
    }
}
