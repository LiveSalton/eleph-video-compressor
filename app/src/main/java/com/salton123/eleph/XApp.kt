package com.salton123.eleph

import android.app.Activity
import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.salton123.eleph.video.compressor.manager.AdMobManager
import com.salton123.manager.ActivityLifeCycleManager
import kt.log
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

}