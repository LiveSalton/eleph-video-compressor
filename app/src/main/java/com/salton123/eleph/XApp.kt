package com.salton123.eleph

import android.app.Application
import com.bumptech.glide.Glide
import com.salton123.eleph.video.compressor.manager.AdMobManager
import com.salton123.manager.ActivityLifeCycleManager
import org.xutils.x

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 16:11
 * ModifyTime: 16:11
 * Description:
 */
class XApp : Application() {
    override fun onCreate() {
        super.onCreate()
        x.Ext.init(this)
        AdMobManager.init()
        ActivityLifeCycleManager.INSTANCE.init(this)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).onTrimMemory(level)
    }
}