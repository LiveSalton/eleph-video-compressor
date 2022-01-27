package com.salton123.video;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.salton123.base.BaseActivity;
import com.salton123.base.feature.ImmersionFeature;
import com.salton123.base.feature.PermissionFeature;
import com.salton123.video.bean.LiveAssetCategoryList;
import com.salton123.video.bean.LiveChannelInfo;
import com.salton123.video.bean.LivePlayUrlM3u8;
import com.salton123.video.bean.VodInfo;
import com.salton123.video.player.VideoBean;
import com.salton123.video.player.VideoViewWrapper;
import com.salton123.view.ShowListWheelView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/27 14:15
 * ModifyTime: 14:15
 * Description:
 */
public class VideoPlayActivity extends BaseActivity implements RequestUtil.HttpResponseCallback {
    private ImmersionFeature mImmersionFeature;
    private static final String TAG = "VideoPlayActivity";
    private VideoViewWrapper videoPlayer;
    private String liveAssetCategoryListUrl = "http://ott.liveapi.mgtv.com/v1/epg/turnplay/getLiveAssetCategoryList?mac_id=F4-60-E2-AE-B8-92&net_id=&device_id=71f110234939485693ecdef05cf7f2a93ddaaa6b&uuid=mgtvmacF460E2AEB892&license=ZgOOgo5MjkyOTAUOvw4OBkuqtA2qDbR8hyANqnY7e3a%2FBoc7vwW%2FS5UNqnt7lZWVIJmqjkyOTI5MZgOOgg%3D%3D&ticket=&buss_id=1000014&version=5.6.307.200.2.DBEI.0.0_Release&platform=3&type=3&mf=Xiaomi&mode=Redmi+6+Pro&_support=00100000011&media_asset_id=RollingBroadcast&pre=0";
    private String livePlayUrlM3u8 = "http://ott.liveapi.mgtv.com/v1/epg/turnplay/getLivePlayUrlM3u8?mac_id=F4-60-E2-AE-B8-92&net_id=&device_id=71f110234939485693ecdef05cf7f2a93ddaaa6b&uuid=mgtvmacF460E2AEB892&license=ZgOOgo5MjkyOTAUOvw4OBkuqtA2qDbR8hyANqnY7e3a%2FBoc7vwW%2FS5UNqnt7lZWVIJmqjkyOTI5MZgOOgg%3D%3D&ticket=&buss_id=1000014&version=5.6.307.200.2.DBEI.0.0_Release&platform=3&type=3&mf=Xiaomi&mode=Redmi+6+Pro&_support=00100000011&definition=2&after_day=1&channel_id=";

    private LivePlayUrlM3u8 mLivePlayUrlM3u8;
    private ShowListWheelView mShowListWheelView;
    private FrameLayout llRoot;

    @Override
    public int getLayout() {
        return R.layout.aty_video_play;
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
        mImmersionFeature = new ImmersionFeature(this);
        addFeature(mImmersionFeature);
    }

    @Override
    public void initViewAndData() {
        addFeature(new PermissionFeature(this));
        videoPlayer = findViewById(R.id.videoPlayer);
        RequestUtil.get(liveAssetCategoryListUrl, LiveAssetCategoryList.class, this);
        mShowListWheelView = findViewById(R.id.showListWheelView);
        llRoot = findViewById(R.id.llRoot);
        llRoot.setOnClickListener(v -> {
            if (mShowListWheelView.getVisibility() == View.VISIBLE) {
                mShowListWheelView.setVisibility(View.GONE);
            } else {
                mShowListWheelView.setVisibility(View.VISIBLE);
            }
        });
        mShowListWheelView.setPlayListCallback(item -> RequestUtil.get(livePlayUrlM3u8 + item.getId(), LivePlayUrlM3u8.class, this));
    }

    @Override
    public void onSuccess(Object responseData) {
        if (responseData instanceof LiveAssetCategoryList) {
            mShowListWheelView.updateWheelView((LiveAssetCategoryList) responseData);
        } else if (responseData instanceof LiveChannelInfo) {

        } else if (responseData instanceof LivePlayUrlM3u8) {
            mLivePlayUrlM3u8 = (LivePlayUrlM3u8) responseData;
            String text = mLivePlayUrlM3u8.getData().getPlay_list().get(0).getUrl();
            RequestUtil.get(text, VodInfo.class, this);
        } else if (responseData instanceof VodInfo) {
            VodInfo info = (VodInfo) responseData;
            String text = mLivePlayUrlM3u8.getData().getPlay_list().get(0).getText();
            String url = info.getInfo();
            videoPlayer.post(() -> {
                videoPlayer.updatePlayUrl(new VideoBean(0, url));
                videoPlayer.startPlay();
            });
            Log.i(TAG, "title:" + text + ",url:" + url);
        }
    }

    @Override
    public void onFailure(String failedReason) {
        Toast.makeText(getApplicationContext(), "error:" + failedReason, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.pausePlay();
    }


}
