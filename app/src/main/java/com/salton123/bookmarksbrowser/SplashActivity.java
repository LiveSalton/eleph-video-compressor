package com.salton123.bookmarksbrowser;

import android.os.Bundle;
import android.view.View;

import com.salton123.base.feature.PermissionFeature;
import com.salton123.bookmarksbrowser.manager.BrowserEntity;
import com.salton123.bookmarksbrowser.manager.BrowserManager;
import com.salton123.bookmarksbrowser.ui.fm.BrowserFragment;
import com.salton123.bookmarksbrowser.ui.fm.BrowserListPopupComp;
import com.salton123.bookmarksbrowser.ui.fm.MenuPopupComp;
import com.salton123.bookmarksbrowser.ui.fm.TitleMorePopupComp;
import com.salton123.eleph.R;
import com.salton123.utils.FragmentUtil;


/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 18:18
 * ModifyTime: 18:18
 * Description:
 */
public class SplashActivity extends BookBaseActivity {

    private BrowserFragment mCurrentBrowserFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public View getTitleBar() {
        return inflater().inflate(R.layout.default_search_title, null);
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
        // addFeature(new EventBusFeature(this));
        addFeature(new PermissionFeature(this));
    }

    @Override
    public void initViewAndData() {
        setListener(R.id.tvActionLeft, R.id.tvActionRight, R.id.tvActionHome,
                R.id.tvActionWindows, R.id.tvActionMenu, R.id.tvTitleMore);
        addBrowserInstance();
    }

    private void addBrowserInstance() {
        BrowserEntity entity = BrowserEntity.newInstance();
        mCurrentBrowserFragment = entity.fragment;
        BrowserManager.INSTANCE.add(entity);
        FragmentUtil.add(getFragmentManager(), entity.fragment, R.id.flContainer, entity.tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvActionLeft:
                if (mCurrentBrowserFragment != null) {
                    mCurrentBrowserFragment.goBack();
                }
                break;
            case R.id.tvActionRight:
                if (mCurrentBrowserFragment != null) {
                    mCurrentBrowserFragment.goForward();
                }
                break;
            case R.id.tvActionHome:
                mCurrentBrowserFragment = new BrowserFragment();
                FragmentUtil.add(getFragmentManager(), mCurrentBrowserFragment, R.id.flContainer, "BrowserFragment");
                break;
            case R.id.tvActionWindows:
                new BrowserListPopupComp().show(getFragmentManager(), "BrowserListPopupComp");
                break;
            case R.id.tvActionMenu:
                new MenuPopupComp().show(getFragmentManager(), "MenuPopupComp");
                break;
            case R.id.tvTitleMore:
                new TitleMorePopupComp().show(getFragmentManager(), "TitleMorePopupWindow");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BrowserManager.INSTANCE.clear();
    }
}
