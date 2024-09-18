package com.example.analytics1.view.activity.native_ad.native_banner

import com.example.analytics1.R
import com.example.analytics1.ads.NativeManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeBannerAdsBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.NothingActivity

class NativeBannerAdsActivity : BaseActivity<ActivityNativeBannerAdsBinding>() {
    override fun getActivityBinding() = ActivityNativeBannerAdsBinding.inflate(layoutInflater)
    private var nativeManager: NativeManager? = null

    override fun loadAds() {
        super.loadAds()
        nativeManager = NativeManager.newInstance(this, getString(R.string.native_ad_unit_id))
        // Load a native ad and bind it to the NativeAdView
        nativeManager?.loadNativeAd { nativeAd ->
            if (nativeAd != null) {
                nativeManager?.loadAdTemplate(
                    nativeAd,
                    R.layout.native_ad_template_banner,
                    binding.layoutAds
                )
            }
        }
    }

    override fun clickView() {
        super.clickView()
        binding.btnNothing.setOnClickListener {
            openActivity(NothingActivity::class.java)
        }
    }

    /** Called before the activity is destroyed. */
    public override fun onDestroy() {
        nativeManager?.destroyNative()
        super.onDestroy()
    }
}