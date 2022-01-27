package com.salton123.video.player;

public interface IPlayerControl {
    //更新播放地址
    void updatePlayUrl(VideoBean videoBean);

    void setVideoListener(IVideoListener listener);

    void startPlay();

    void pausePlay();

    long getDuration();

    long getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    void onPause();

    void onResume();

    void onDestory();
}