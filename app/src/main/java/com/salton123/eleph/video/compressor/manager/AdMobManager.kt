package com.salton123.eleph.video.compressor.manager

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kt.log
import org.xutils.x

/**
 * Time:2022/2/16 5:52 下午
 * Author:
 * Description:
 */
object AdMobManager {
    fun init() {
        MobileAds.initialize(x.app()) {
            log("init admob:${it.adapterStatusMap}")
        }
    }

    fun loadAd(activity: Activity) {
        val adRequest: AdRequest = AdRequest.Builder().build()
        RewardedAd.load(activity, "ca-app-pub-2681886362144670/3052521243",
            adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // Handle the error.
                log(loadAdError.message)
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                log("Ad was loaded.")
                rewardedAd.show(activity) {
                    log("onUserEarnedReward,${it.amount}")
                }
            }
        })
    }
}