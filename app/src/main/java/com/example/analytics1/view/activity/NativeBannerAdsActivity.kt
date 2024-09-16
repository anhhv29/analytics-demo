package com.example.analytics1.view.activity

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeBannerAdsBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class NativeBannerAdsActivity : BaseActivity<ActivityNativeBannerAdsBinding>() {
    override fun getActivityBinding() = ActivityNativeBannerAdsBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.btnNothing.setOnClickListener {
            openActivity(NothingActivity::class.java)
        }
    }

    /** Called before the activity is destroyed. */
    public override fun onDestroy() {
        //
        super.onDestroy()
    }
}