package com.salton123.eleph.video.compressor.ui

import android.view.WindowManager
import com.salton123.base.BaseDialogFragment
import com.salton123.eleph.BuildConfig
import com.salton123.utils.ScreenUtils

/**
 * User: newSalton@outlook.com
 * Date: 2019/8/20 15:52
 * ModifyTime: 15:52
 * Description:
 */
abstract class FullScreenDialogFragment : BaseDialogFragment() {

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            val params = window!!.attributes
            val width = ScreenUtils.getScreenWidth() - ScreenUtils.dp2px(72f)
            params.width = width
            params.height = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = params
//            window.setWindowAnimations(R.style.dialog_anim)
            dialog.setCancelable(BuildConfig.DEBUG)
            dialog.setCanceledOnTouchOutside(BuildConfig.DEBUG)
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.decorView.setPadding(0, 0, 0, 0)
            window.setDimAmount(0.7f)
        }
    }
}