package com.example.analytics1.view.activity.native_ad.native_large

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeLargeBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class NativeLargeActivity : BaseActivity<ActivityNativeLargeBinding>() {
    override fun getActivityBinding() = ActivityNativeLargeBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnNativeLargeAds.setOnClickListener {
                openActivity(NativeLargeAdsActivity::class.java)
            }

            btnNativeLargeOld.setOnClickListener {
                openActivity(NativeLargeOldActivity::class.java)
            }
        }
    }
}