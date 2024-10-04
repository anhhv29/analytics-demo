package com.example.analytics1.view.activity.banner_ad

import com.example.analytics1.R
import com.example.analytics1.ads.CollapsibleBannerManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityCollapsibleBannerBinding
import com.example.analytics1.util.MyUtils.Companion.goneView
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.NothingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollapsibleBannerActivity : BaseActivity<ActivityCollapsibleBannerBinding>() {
    override fun getActivityBinding() = ActivityCollapsibleBannerBinding.inflate(layoutInflater)

    private var collapsibleBannerManager: CollapsibleBannerManager? = null

    override fun loadAds() {
        super.loadAds()

        CoroutineScope(Dispatchers.IO).launch {
            runOnUiThread {
                binding.apply {
                    shimmerView.startShimmer()
                    // Load an ad on the main thread.
                    collapsibleBannerManager = CollapsibleBannerManager.newInstance(
                        this@CollapsibleBannerActivity,
                        getString(R.string.collapsible_banner_ad_unit_id)
                    )
                    collapsibleBannerManager?.loadCollapsibleBanner(layoutAds) {
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
        collapsibleBannerManager?.pauseBanner()
        super.onPause()
    }

    /** Called when returning to the activity. */
    public override fun onResume() {
        super.onResume()
        collapsibleBannerManager?.resumeBanner()

        /** Resume CollapsibleBanner. */
        /*
        if (!MyApplication.isShowAppOpenAdComplete) {
            collapsibleBannerManager?.loadCollapsibleBanner(binding.layoutAds)
        } else {
            MyApplication.isShowAppOpenAdComplete = false
        }

         */
    }

    /** Called before the activity is destroyed. */
    public override fun onDestroy() {
        collapsibleBannerManager?.destroyBanner()
        super.onDestroy()
    }
}