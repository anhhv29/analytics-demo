package com.example.analytics1.view.activity

import android.view.LayoutInflater
import com.example.analytics1.R
import com.example.analytics1.ads.CollapsibleBannerManager
import com.example.analytics1.application.MyApplication
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityCollapsibleBannerBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollapsibleBannerActivity : BaseActivity<ActivityCollapsibleBannerBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityCollapsibleBannerBinding =
        ActivityCollapsibleBannerBinding.inflate(layoutInflater)

    private var collapsibleBannerManager: CollapsibleBannerManager? = null

    override fun loadAds() {
        super.loadAds()

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@CollapsibleBannerActivity) {}
            runOnUiThread {
                // Load an ad on the main thread.
                collapsibleBannerManager = CollapsibleBannerManager.newInstance(
                    this@CollapsibleBannerActivity,
                    getString(R.string.collapsible_banner_ad_unit_id)
                )
                collapsibleBannerManager?.loadCollapsibleBanner(binding.layoutAds)
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