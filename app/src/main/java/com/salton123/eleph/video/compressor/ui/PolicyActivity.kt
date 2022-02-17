package com.salton123.eleph.video.compressor.ui

import android.text.TextUtils
import android.webkit.WebView
import com.salton123.base.BaseActivity
import com.salton123.eleph.R
import org.xutils.x

/**
 * User: newSalton@outlook.com
 * Date: 2019/8/17 16:40
 * ModifyTime: 16:40
 * Description:
 */
class PolicyActivity : BaseActivity() {
    var url = "https://www.salton123.com/privacy-policy"
    var title = x.app().getString(R.string.privacy_policy)
    override fun getLayoutId(): Int = R.layout.aty_policy

    private lateinit var webView: WebView
    override fun initViewAndData() {
        super.initViewAndData()
        tvTitle.text = title
        webView = f(R.id.webView)
        var targetUrl = intent?.getStringExtra("url") ?: ""
        if (!TextUtils.isEmpty(targetUrl)) {
            url = targetUrl
        }
        var targetTitle = intent?.getStringExtra("title") ?: ""
        if (!TextUtils.isEmpty(targetTitle)) {
            title = targetTitle
        }
        webView.loadUrl(url)
    }
}