package com.example.analytics1.view.activity

import android.view.LayoutInflater
import com.example.analytics1.R
import com.example.analytics1.ads.BannerManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityBannerAdsBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BannerAdsActivity : BaseActivity<ActivityBannerAdsBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityBannerAdsBinding =
        ActivityBannerAdsBinding.inflate(layoutInflater)

    private var bannerManager: BannerManager? = null
    override fun loadAds() {
        super.loadAds()

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@BannerAdsActivity) {}
            runOnUiThread {
                // Load an ad on the main thread.
                bannerManager = BannerManager.newInstance(
                    this@BannerAdsActivity,
                    getString(R.string.banner_ad_unit_id)
                )
                bannerManager?.loadBanner(binding.layoutAds)
            }
        }
    }

    override fun clickView() {
        super.clickView()
        binding.btnNothing.setOnClickListener {
            openActivity(NothingActivity::class.java)
        }
    }

    /** Called when leaving the activity. */
    public override fun onPause() {
        bannerManager?.pauseBanner()
        super.onPause()
    }

    /** Called when returning to the activity. */
    public override fun onResume() {
        super.onResume()
        bannerManager?.resumeBanner()
    }

    /** Called before the activity is destroyed. */
    public override fun onDestroy() {
        bannerManager?.destroyBanner()
        super.onDestroy()
    }
}