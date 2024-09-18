package com.example.analytics1.view.activity.native_ad.native_medium

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeBinding
import com.example.analytics1.databinding.ActivityNativeMediumBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.native_ad.native_banner.NativeBannerActivity
import com.example.analytics1.view.activity.native_ad.native_small.NativeSmallActivity
import com.example.analytics1.view.activity.banner_ad.CollapsibleBannerActivity
import com.example.analytics1.view.activity.native_ad.NativeLoadMultipleAdsActivity
import com.example.analytics1.view.activity.native_ad.native_large.NativeLargeActivity

class NativeMediumActivity : BaseActivity<ActivityNativeMediumBinding>() {
    override fun getActivityBinding() = ActivityNativeMediumBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnNativeMediumUnified.setOnClickListener {
                openActivity(NativeMediumUnifiedActivity::class.java)
            }

            btnNativeMediumAds.setOnClickListener {
                openActivity(NativeMediumAdsActivity::class.java)
            }

            btnNativeMediumOld.setOnClickListener {
                openActivity(NativeMediumOldActivity::class.java)
            }

            btnNativeMediumSmart.setOnClickListener {
                openActivity(NativeMediumSmartActivity::class.java)
            }

            btnNativeMediumOldSmart.setOnClickListener {
                openActivity(NativeMediumOldSmartActivity::class.java)
            }

            btnNativeMediumGnt.setOnClickListener {
                openActivity(NativeMediumGntActivity::class.java)
            }
        }
    }
}