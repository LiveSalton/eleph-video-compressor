package com.salton123.eleph.video.compressor.manager

import android.app.Activity
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.salton123.eleph.BuildConfig
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
        RewardedAd.load(activity, BuildConfig.GOOGLE_REWARD_ID,
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

    fun loadBannerAd(activity: Activity, viewGroup: ViewGroup) {
        val banner = AdView(activity)
        banner.adSize = AdSize.BANNER
        banner.adUnitId = BuildConfig.GOOGLE_BANNER_ID
        banner.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                log("banner onAdFailedToLoad:$p0")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                log("banner onAdClicked")
            }

            override fun onAdClosed() {
                super.onAdClosed()
                log("banner onAdClosed")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                log("banner onAdLoaded")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                log("banner onAdOpened")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                log("banner onAdImpression")
            }
        }
        val adRequest: AdRequest = AdRequest.Builder().build()
        banner.loadAd(adRequest)
        viewGroup.removeAllViews()
        viewGroup.addView(banner)
    }
}