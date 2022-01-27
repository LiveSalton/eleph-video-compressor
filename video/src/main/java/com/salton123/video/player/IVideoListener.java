package com.salton123.video.player;

public interface IVideoListener {

    /**
     * 播放暂停
     */
    void onVideoStarted();

    /**
     * 继续播放
     */
    void onVideoPaused();

    /**
     * 播放完成
     */
    void onComplete();

    /**
     * 准备播放完成
     */
    void onPrepared();

    /**
     * 播放出错
     */
    void onError();

    /**
     * 播放器内部事件回调
     */
    void onInfo(int what, int extra);

}
