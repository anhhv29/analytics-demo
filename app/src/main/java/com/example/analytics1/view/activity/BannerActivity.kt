package com.example.analytics1.view.activity

import android.view.LayoutInflater
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityBannerBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class BannerActivity : BaseActivity<ActivityBannerBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityBannerBinding =
        ActivityBannerBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnBannerAds.setOnClickListener {
                openActivity(BannerAdsActivity::class.java)
            }
        }
    }
}