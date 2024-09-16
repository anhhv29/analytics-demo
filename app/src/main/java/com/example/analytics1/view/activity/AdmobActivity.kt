package com.example.analytics1.view.activity

import androidx.activity.OnBackPressedCallback
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityAdmobBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class AdmobActivity : BaseActivity<ActivityAdmobBinding>() {
    override fun getActivityBinding() = ActivityAdmobBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        // Override the default implementation when the user presses the back key.
        val onBackPressedCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.apply {
            btnBanner.setOnClickListener {
                openActivity(BannerActivity::class.java)
            }

            btnInterstitial.setOnClickListener {
                openActivity(InterstitialActivity::class.java)
            }

            btnNative.setOnClickListener {

            }

            btnRewarded.setOnClickListener {
                openActivity(RewardedActivity::class.java)
            }
        }
    }
}