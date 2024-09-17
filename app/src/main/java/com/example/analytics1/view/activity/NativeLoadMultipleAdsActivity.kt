package com.example.analytics1.view.activity

import android.annotation.SuppressLint
import com.example.analytics1.R
import com.example.analytics1.ads.NativeManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeBannerAdsBinding
import com.example.analytics1.databinding.ActivityNativeLoadMutipleAdsBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class NativeLoadMultipleAdsActivity : BaseActivity<ActivityNativeLoadMutipleAdsBinding>() {
    override fun getActivityBinding() = ActivityNativeLoadMutipleAdsBinding.inflate(layoutInflater)
    private var nativeManager: NativeManager? = null

    @SuppressLint("InflateParams")
    override fun loadAds() {
        super.loadAds()
        nativeManager = NativeManager.newInstance(this, getString(R.string.native_ad_unit_id))

        // Load multiple native ads (e.g., 3 ads)
        nativeManager?.loadMultipleAds(3) { nativeAds ->
            if (nativeAds.isNotEmpty()) {
                // Load ads into respective frames using the ad templates
                nativeManager?.loadAdTemplate(
                    nativeAds[0],
                    R.layout.gnt_medium_template_view,
                    binding.adFrame1
                )
                nativeManager?.loadAdTemplate(
                    nativeAds[1],
                    R.layout.gnt_small_template_view,
                    binding.adFrame2
                )
                nativeManager?.loadAdTemplate(
                    nativeAds[2],
                    R.layout.gnt_small_template_view,
                    binding.adFrame3
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