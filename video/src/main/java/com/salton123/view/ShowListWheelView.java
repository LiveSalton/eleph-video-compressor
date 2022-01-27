package com.salton123.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.salton123.adapter.abslistview.ViewHolder;
import com.salton123.base.AdapterBase;
import com.salton123.video.R;
import com.salton123.video.bean.LiveAssetCategoryList;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/27 16:32
 * ModifyTime: 16:32
 * Description:
 */
public class ShowListWheelView extends LinearLayout {
    private ListView lvMainType, lvSubType;
    private LiveAssetCategoryList mData;
    private MainTypeAdapter mMainTypeAdapter;
    private SubTypeAdapter mSubTypeAdapter;
    private PlayListCallback mPlayListCallback;

    public ShowListWheelView(Context context) {
        this(context, null);
    }

    public ShowListWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowListWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewAndData();
    }

    private void initViewAndData() {
        LayoutInflater.from(getContext()).inflate(R.layout.stub_show_list_wheel_view, this);
        lvMainType = findViewById(R.id.lvMainType);
        lvSubType = findViewById(R.id.lvSubType);
        mMainTypeAdapter = new MainTypeAdapter(getContext());
        mSubTypeAdapter = new SubTypeAdapter(getContext());
        lvMainType.setAdapter(mMainTypeAdapter);
        lvMainType.setOnItemClickListener((parent, view, position, id) -> {
            LiveAssetCategoryList.DataBean.CategoryBean item = (LiveAssetCategoryList.DataBean.CategoryBean) parent.getItemAtPosition(position);
            mSubTypeAdapter.addAll(item.getChannels());
        });
        lvSubType.setAdapter(mSubTypeAdapter);
        lvSubType.setOnItemClickListener((parent, view, position, id) -> {
            LiveAssetCategoryList.DataBean.CategoryBean.ChannelsBean item = (LiveAssetCategoryList.DataBean.CategoryBean.ChannelsBean) parent.getItemAtPosition(position);
            if (mPlayListCallback != null) {
                mPlayListCallback.onPlayList(item);
            }
        });
    }

    public void updateWheelView(LiveAssetCategoryList data) {
        if (mMainTypeAdapter != null) {
            mData = data;
            mMainTypeAdapter.addAll(data.getData().getCategory());
        }
    }

    public void setPlayListCallback(PlayListCallback playListCallback) {
        mPlayListCallback = playListCallback;
    }


    static class MainTypeAdapter extends AdapterBase<LiveAssetCategoryList.DataBean.CategoryBean> {

        public MainTypeAdapter(Context pContext) {
            super(pContext, R.layout.adapter_item_show_list_main_type);
        }

        @Override
        protected void convert(ViewHolder viewHolder, LiveAssetCategoryList.DataBean.CategoryBean item, int position) {
            viewHolder.setText(R.id.tvName, item.getName());
        }
    }

    static class SubTypeAdapter extends AdapterBase<LiveAssetCategoryList.DataBean.CategoryBean.ChannelsBean> {

        public SubTypeAdapter(Context context) {
            super(context, R.layout.adapter_item_show_list_sub_type);
        }

        @Override
        protected void convert(ViewHolder viewHolder, LiveAssetCategoryList.DataBean.CategoryBean.ChannelsBean item, int position) {
            viewHolder.setText(R.id.tvName, item.getName())
                    .setText(R.id.tvSubName, item.getCurr_program());
        }
    }

    public interface PlayListCallback {
        void onPlayList(LiveAssetCategoryList.DataBean.CategoryBean.ChannelsBean item);
    }
}
