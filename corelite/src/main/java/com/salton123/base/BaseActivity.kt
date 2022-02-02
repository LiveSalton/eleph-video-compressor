package com.salton123.base

import android.os.Bundle
import android.widget.TextView
import com.salton123.base.feature.ImmersionFeature
import com.salton123.corelite.R

/**
 * Time:2022/2/2 5:29 下午
 * Author:
 * Description:
 */
abstract class BaseActivity : DelegateActivity() {
    override fun getLayout(): Int = R.layout.activity_base
    private lateinit var mImmersionFeature: ImmersionFeature
    private lateinit var tvBack: TextView
    private lateinit var tvMore: TextView
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
    }

    abstract fun getLayoutId(): Int
    open fun toMoreAction() {
    }

    override fun initViewAndData() {
        tvBack = findViewById(R.id.tvBack)
        tvMore = findViewById(R.id.tvMore)
        tvBack.setOnClickListener {
            finish()
        }
        tvMore.setOnClickListener {
            toMoreAction()
        }
    }
}