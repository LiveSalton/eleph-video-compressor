package com.salton123.base

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.salton123.base.feature.ImmersionFeature
import com.salton123.eleph.R

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
    private lateinit var llContent: LinearLayout
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
    }

    abstract fun getLayoutId(): Int
    open fun toMoreAction() {
    }

    override fun initViewAndData() {
        llContent = findViewById(R.id.llContent)
        View.inflate(this, getLayoutId(), llContent)
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