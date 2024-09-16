package com.example.analytics1.view.activity

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class NativeActivity : BaseActivity<ActivityNativeBinding>() {
    override fun getActivityBinding() = ActivityNativeBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnNativeBanner.setOnClickListener {
                openActivity(NativeBannerActivity::class.java)
            }

            btnNativeSmall.setOnClickListener {
                openActivity(CollapsibleBannerActivity::class.java)
            }

            btnNativeMedium.setOnClickListener {
                openActivity(CollapsibleBannerActivity::class.java)
            }

            btnNativeLarge.setOnClickListener {
                openActivity(CollapsibleBannerActivity::class.java)
            }

            btnNativeInline.setOnClickListener {
                openActivity(CollapsibleBannerActivity::class.java)
            }
        }
    }
}