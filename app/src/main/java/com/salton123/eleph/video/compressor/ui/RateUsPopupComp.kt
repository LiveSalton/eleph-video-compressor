package com.salton123.eleph.video.compressor.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.salton123.base.BaseDialogFragment
import com.salton123.eleph.BuildConfig
import com.salton123.eleph.R
import com.salton123.utils.ScreenUtils
import org.xutils.x

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/19 14:20
 * ModifyTime: 14:20
 * Description:
 */
class RateUsPopupComp : BaseDialogFragment() {

    private lateinit var tvCancel: TextView
    private lateinit var tvFiveStar: TextView
    private lateinit var tvFourStar: TextView
    override fun getLayout(): Int = R.layout.comp_rate_us
    override fun initVariable(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.GeneralDialog)
    }

    override fun initViewAndData() {
        tvCancel = f(R.id.tvCancel)
        tvFiveStar = f(R.id.tvFiveStar)
        tvFourStar = f(R.id.tvFourStar)
        tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }
        tvFiveStar.setOnClickListener {

            val packageName = x.app().packageName
            var stringBuilder: StringBuilder
            try {
                stringBuilder = StringBuilder()
                stringBuilder.append("market://details?id=")
                stringBuilder.append(packageName)
                activity().startActivity(Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())))
            } catch (unused: ActivityNotFoundException) {
                stringBuilder = StringBuilder()
                stringBuilder.append("https://play.google.com/store/apps/details?id=")
                stringBuilder.append(packageName)
                activity().startActivity(Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())))
            }
            dismissAllowingStateLoss()
        }
        tvFourStar.setOnClickListener {
            dismissAllowingStateLoss()
            val intent = Intent("android.intent.action.SENDTO")
            intent.data = Uri.parse("mailto:newsalton@163.com")
            intent.putExtra("android.intent.extra.SUBJECT", "${getString(R.string.app_name)} - ${getString(R.string.app_description)}, Android")
            val stringBuilder = StringBuilder()
            stringBuilder.append("${getString(R.string.app_name)}_Android_" + BuildConfig.VERSION_NAME + "_" + BuildConfig.GIT_HASH)
            stringBuilder.append(Build.VERSION.SDK_INT)
            stringBuilder.append('_')
            stringBuilder.append(Build.MODEL)
            stringBuilder.append(10)
            intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString())
            activity().startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        ScreenUtils.hideNavigationBar(window)
        val params = window.attributes
        params.gravity = Gravity.CENTER
        val width = ScreenUtils.getScreenWidth() - ScreenUtils.dp2px(60f)
        params.width = width
        window.attributes = params
        window.setWindowAnimations(R.style.slide_popup_ani)
    }
}
