package com.example.analytics1.view.activity.native_ad.native_banner

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeBannerBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class NativeBannerActivity : BaseActivity<ActivityNativeBannerBinding>() {
    override fun getActivityBinding() = ActivityNativeBannerBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnNativeBanner.setOnClickListener {
                openActivity(NativeBannerAdsActivity::class.java)
            }

            btnNativeBannerOld.setOnClickListener {
                openActivity(NativeBannerOldActivity::class.java)
            }

            btnNativeBannerSmart.setOnClickListener {
                openActivity(NativeBannerSmartActivity::class.java)
            }
        }
    }
}