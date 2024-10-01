package com.example.analytics1.view.activity.native_ad.native_full_screen

import com.example.analytics1.R
import com.example.analytics1.ads.NativeFullManager
import com.example.analytics1.application.MyApplication
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeFullScreenAdsBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.NothingActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

class NativeFullScreenAdsActivity : BaseActivity<ActivityNativeFullScreenAdsBinding>() {
    override fun getActivityBinding() = ActivityNativeFullScreenAdsBinding.inflate(layoutInflater)
    private var nativeFullManager: NativeFullManager? = null
    override fun loadAds() {
        super.loadAds()

        nativeFullManager =
            NativeFullManager.newInstance(this, getString(R.string.native_full_screen_ad_unit_id))
        // Load a native ad and bind it to the NativeAdView
        nativeFullManager?.loadNativeAd { nativeAd ->
            if (nativeAd != null) {
                nativeFullManager?.loadAdTemplate(
                    nativeAd,
                    R.layout.native_ad_template_full_screen,
                    binding.layoutAds
                )
            }
        }
    }

    override fun clickView() {
        super.clickView()
        binding.btnNext.setOnClickListener {
            openActivity(NothingActivity::class.java)
        }
    }

    /** Called before the activity is destroyed. */
    public override fun onDestroy() {
        nativeFullManager?.destroyNative()
        super.onDestroy()
    }
}