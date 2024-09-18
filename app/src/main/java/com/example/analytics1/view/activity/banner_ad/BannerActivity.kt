package com.example.analytics1.view.activity.banner_ad

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityBannerBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class BannerActivity : BaseActivity<ActivityBannerBinding>() {
    override fun getActivityBinding() = ActivityBannerBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnBannerAds.setOnClickListener {
                openActivity(BannerAdsActivity::class.java)
            }

            btnCollapsibleBannerAds.setOnClickListener {
                openActivity(CollapsibleBannerActivity::class.java)
            }
        }
    }
}