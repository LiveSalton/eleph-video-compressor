package com.salton123.eleph.video.compressor.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import com.hjq.widget.layout.SettingBar
import com.salton123.base.BaseActivity
import com.salton123.eleph.BuildConfig
import com.salton123.eleph.R

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/22 21:31
 * ModifyTime: 21:31
 * Description:
 */
class SettingActivity : BaseActivity() {
    private lateinit var sb_setting_about: SettingBar
    private lateinit var sb_setting_contact_us: SettingBar
    private lateinit var sb_setting_rate_us: SettingBar
    private lateinit var sb_setting_policy: SettingBar
    private lateinit var sb_setting_terms: SettingBar
    override fun initViewAndData() {
        super.initViewAndData()
        tvTitle.text = getString(R.string.setting_title)
        sb_setting_about = f(R.id.sb_setting_about)
        sb_setting_contact_us = f(R.id.sb_setting_contact_us)
        sb_setting_rate_us = f(R.id.sb_setting_rate_us)
        sb_setting_policy = f(R.id.sb_setting_policy)
        sb_setting_terms = f(R.id.sb_setting_terms)
        sb_setting_about.setOnClickListener {
//            startActivity(Intent())

        }
        sb_setting_contact_us.setOnClickListener {
            val intent = Intent("android.intent.action.SENDTO")
            intent.data = Uri.parse("mailto:newsalton@163.com")
            intent.putExtra("android.intent.extra.SUBJECT", "Eleph Player - Your Video Player And Cleaner, Android")
            val stringBuilder = StringBuilder()
            stringBuilder.append("Eleph Player_Android_" + BuildConfig.VERSION_NAME + "_" + BuildConfig.GIT_HASH)
            stringBuilder.append(Build.VERSION.SDK_INT)
            stringBuilder.append('_')
            stringBuilder.append(Build.MODEL)
            stringBuilder.append(10)
            intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString())
            activity().startActivity(intent)
        }
//        sb_setting_rate_us.setOnClickListener {  }
//        sb_setting_policy.setOnClickListener {  }
//        sb_setting_terms.setOnClickListener {  }
    }
//    override fun initViewAndData(savedInstanceState: Bundle?) {
//        sb_setting_exit.visibility = View.GONE
//        sb_setting_exit.setOnClickListener {
//            ProviderManager.user().logout()
//            var path = Constants.Router.App.MAIN
//            ARouter.getInstance().build(path)
//                .navigation(activity())
//            ToastUtils.show(getString(R.string.logouted_account))
//        }
//        setListener(sb_setting_about, sb_setting_contact_us, sb_setting_rate_us, sb_setting_policy, sb_setting_terms)
//    }
//
//    override fun onClick(p0: View?) {
//        super.onClick(p0)
//        when (p0) {
//            sb_setting_about -> {
//                RouterManager.about(activity())
//            }
//            sb_setting_contact_us -> {
//                val intent = Intent("android.intent.action.SENDTO")
//                intent.data = Uri.parse("mailto:newsalton@163.com")
//                intent.putExtra("android.intent.extra.SUBJECT", "Eleph Player - Your Video Player And Cleaner, Android")
//                val stringBuilder = StringBuilder()
//                stringBuilder.append("Eleph Player_Android_" + BuildConfig.VERSION_NAME + "_" + BuildConfig.GIT_HASH)
//                stringBuilder.append(Build.VERSION.SDK_INT)
//                stringBuilder.append('_')
//                stringBuilder.append(Build.MODEL)
//                stringBuilder.append(10)
//                intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString())
//                activity().startActivity(intent)
//            }
//            sb_setting_rate_us -> {
//                RouterManager.rateUs(this)
//            }
//            sb_setting_policy -> {
//                RouterManager.policy(this)
//            }
//            sb_setting_terms -> {
//                RouterManager.termOfService(
//                    this,
//                    getString(R.string.terms_of_service),
//                    "https://www.salton123.com/terms-of-service"
//                )
//            }
//        }
//    }
//
//    override fun getTitleText(): String {
//        return getString(R.string.setting_title)
//    }

    override fun getLayoutId(): Int = R.layout.activity_setting
}