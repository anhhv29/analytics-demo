package com.example.analytics1.view.activity.native_ad

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.native_ad.native_banner.NativeBannerActivity
import com.example.analytics1.view.activity.native_ad.native_small.NativeSmallActivity
import com.example.analytics1.view.activity.banner_ad.CollapsibleBannerActivity
import com.example.analytics1.view.activity.native_ad.native_large.NativeLargeActivity
import com.example.analytics1.view.activity.native_ad.native_medium.NativeMediumActivity

class NativeActivity : BaseActivity<ActivityNativeBinding>() {
    override fun getActivityBinding() = ActivityNativeBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnNativeBanner.setOnClickListener {
                openActivity(NativeBannerActivity::class.java)
            }

            btnNativeSmall.setOnClickListener {
                openActivity(NativeSmallActivity::class.java)
            }

            btnNativeMedium.setOnClickListener {
                openActivity(NativeMediumActivity::class.java)
            }

            btnNativeLarge.setOnClickListener {
                openActivity(NativeLargeActivity::class.java)
            }

            btnNativeInline.setOnClickListener {
                openActivity(CollapsibleBannerActivity::class.java)
            }

            btnNativeLoadMultipleAds.setOnClickListener {
                openActivity(NativeLoadMultipleAdsActivity::class.java)
            }
        }
    }
}