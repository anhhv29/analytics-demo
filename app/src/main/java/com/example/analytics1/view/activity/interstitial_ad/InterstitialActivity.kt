package com.example.analytics1.view.activity.interstitial_ad

import android.util.Log
import com.example.analytics1.R
import com.example.analytics1.ads.InterstitialManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityInterstitialBinding
import com.example.analytics1.util.Constants
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.util.SharedPreferences
import com.example.analytics1.view.activity.NothingActivity
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig

class InterstitialActivity : BaseActivity<ActivityInterstitialBinding>() {
    override fun getActivityBinding() = ActivityInterstitialBinding.inflate(layoutInflater)

    private var interstitialManager: InterstitialManager? = null
    override fun loadAds() {
        super.loadAds()
        if (!SharedPreferences.isProApp(this)) {
            showLoading()
            interstitialManager =
                InterstitialManager.newInstance(this, getString(R.string.interstitial_ad_unit_id))
            interstitialManager?.loadAd(afterLoadAd = {
                hideLoading()
            })
        }
    }

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnLayoutShowInterstitial.setOnClickListener {
                if (!SharedPreferences.isProApp(this@InterstitialActivity)) {
                    interstitialManager?.showInterstitial(afterShowAd = {
                        openActivity(NothingActivity::class.java)
                    })
                } else {
                    openActivity(NothingActivity::class.java)
                }
            }

            btnItemShowInterstitial.setOnClickListener {
                openActivity(InterstitialAdsActivity::class.java)
            }

            btnInterstitialWithCappingTime.setOnClickListener {
                checkCappingTime()
                if (!SharedPreferences.isProApp(this@InterstitialActivity)) {
                    interstitialManager?.showInterstitialWithCappingTime(afterShowAd = {
                        openActivity(NothingActivity::class.java)
                    })
                } else {
                    openActivity(NothingActivity::class.java)
                }
            }
        }
    }

    private fun checkCappingTime() {
        Log.d(
            "scp",
            "time check: ${System.currentTimeMillis() - Constants.RemoteConfig.lastShowAdFull}, time config: ${
                Firebase.remoteConfig.getValue(
                    Constants.RemoteConfig.KEY_INTERVAL_AD_FULL
                ).asLong()
            }"
        )
    }
}