package com.example.analytics1.view.activity.native_ad.native_inline

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeInlineBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class NativeInlineActivity : BaseActivity<ActivityNativeInlineBinding>() {
    override fun getActivityBinding() = ActivityNativeInlineBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnNativeInlineAds.setOnClickListener {
                openActivity(NativeInlineAdsActivity::class.java)
            }

            btnNativeInlineSpanCount.setOnClickListener {
                openActivity(NativeInlineSpanCountActivity::class.java)
            }
        }
    }
}