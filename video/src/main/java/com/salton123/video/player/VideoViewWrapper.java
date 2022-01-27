package com.salton123.video.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.salton123.video.R;

/**
 * User: newSalton@outlook.com
 * Date: 2018/9/15 21:33
 * ModifyTime: 21:33
 * Description:
 */
public class VideoViewWrapper extends FrameLayout implements IPlayerControl {
    private static final String TAG = "VideoViewWrapper";
    protected IVideoListener mVideoListener;
    private boolean isUserStartPlaying = false;
    private long lastPlayingPosition;
    private boolean isPlayComplete = false;
    VideoBean mVideoBean;

    private ImageView ivThumbnail;
    private VideoView videoPlayer;

    public VideoViewWrapper(Context context) {
        super(context);
        initVideoViewWrapper();
    }

    public VideoViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoViewWrapper();
    }

    public VideoViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoViewWrapper();
    }

    private void initVideoViewWrapper() {
        mVideoBean = new VideoBean(0, "");
        LayoutInflater.from(getContext()).inflate(R.layout.video_view_wrapper, this, true);
        ivThumbnail = findViewById(R.id.ivThumbnail);
        videoPlayer = findViewById(R.id.phoneVideoView);
        videoPlayer.setOnErrorListener((mp, what, extra) -> {
            if (mVideoListener != null) {
                mVideoListener.onError();
            }
            return true;
        });
        videoPlayer.setOnCompletionListener(mp -> {
            if (mVideoListener != null) {
                lastPlayingPosition = getDuration();
                isUserStartPlaying = false;
                mVideoListener.onComplete();
                isPlayComplete = true;
            }
        });
        videoPlayer.setOnPreparedListener(mp -> {
            if (mVideoListener != null) {
                mVideoListener.onPrepared();
                isPlayComplete = false;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            videoPlayer.setOnInfoListener((mp, what, extra) -> {
                if (mVideoListener != null) {
                    mVideoListener.onInfo(what, extra);
                }
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: // 视频开始渲染
                        if (mVideoListener != null) {
                            mVideoListener.onVideoStarted();
                            isPlayComplete = false;
                        }
                        ivThumbnail.setVisibility(View.GONE);
                        break;
                }
                return true;
            });
        }
    }

    public void updatePlayUrl(VideoBean videoBean) {
        mVideoBean = videoBean;
        if (mVideoBean.resId == 0) {
            ivThumbnail.setVisibility(View.GONE);
        } else {
            ivThumbnail.setImageResource(mVideoBean.resId);
            ivThumbnail.setVisibility(View.VISIBLE);
        }
        videoPlayer.setVideoURI(mVideoBean.uri);
        Log.i(TAG,"uri:"+mVideoBean.uri.toString());
    }

    @Override
    public boolean isPlaying() {
        return videoPlayer.isPlaying();
    }

    @Override
    public void setVideoListener(IVideoListener listener) {
        this.mVideoListener = listener;
    }

    @Override
    public long getDuration() {
        return videoPlayer.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return videoPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(long pos) {
        videoPlayer.seekTo((int) pos);
    }

    @Override
    public void startPlay() {
        if (videoPlayer.isPlaying()){
            videoPlayer.pause();
        }
        videoPlayer.start();
        isUserStartPlaying = true;
        if (mVideoListener != null) {
            mVideoListener.onVideoStarted();
        }
    }

    @Override
    public void pausePlay() {
        lastPlayingPosition = getCurrentPosition();
        videoPlayer.pause();
    }

    @Override
    public void onPause() {
        if (isPlaying()) {
            isUserStartPlaying = true;
        }
        if (!isPlayComplete) {
            lastPlayingPosition = getCurrentPosition();
            videoPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        seekTo(lastPlayingPosition);
        if (isUserStartPlaying) {
            updatePlayUrl(mVideoBean);
            startPlay();
        }
    }

    @Override
    public void onDestory() {

    }
}
