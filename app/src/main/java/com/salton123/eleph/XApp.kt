package com.salton123.eleph

import android.app.Application
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
        ActivityLifeCycleManager.INSTANCE.init(this)
    }
}