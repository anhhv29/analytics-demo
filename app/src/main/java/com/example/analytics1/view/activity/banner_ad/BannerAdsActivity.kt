package com.example.analytics1.view.activity.banner_ad

import com.example.analytics1.R
import com.example.analytics1.ads.BannerManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityBannerAdsBinding
import com.example.analytics1.util.MyUtils.Companion.goneView
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.util.MyUtils.Companion.visibleView
import com.example.analytics1.view.activity.NothingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BannerAdsActivity : BaseActivity<ActivityBannerAdsBinding>() {
    override fun getActivityBinding() = ActivityBannerAdsBinding.inflate(layoutInflater)

    private var bannerManager: BannerManager? = null
    override fun loadAds() {
        super.loadAds()
        CoroutineScope(Dispatchers.IO).launch {
            runOnUiThread {
                binding.apply {
                    shimmerView.startShimmer()
                    // Load an ad on the main thread.
                    bannerManager = BannerManager.newInstance(
                        this@BannerAdsActivity,
                        getString(R.string.banner_adaptive_ad_unit_id)
                    )
                    bannerManager?.loadBanner(layoutAds) {
                        shimmerView.stopShimmer()
                        shimmerView.goneView()
                    }
                }
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