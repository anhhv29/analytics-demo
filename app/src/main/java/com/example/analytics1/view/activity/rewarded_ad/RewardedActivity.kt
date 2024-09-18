package com.example.analytics1.view.activity.rewarded_ad

import com.example.analytics1.R
import com.example.analytics1.ads.RewardedManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityRewardedBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.util.SharedPreferences
import com.example.analytics1.view.activity.NothingActivity

class RewardedActivity : BaseActivity<ActivityRewardedBinding>() {
    override fun getActivityBinding() = ActivityRewardedBinding.inflate(layoutInflater)

    private var rewardedManager: RewardedManager? = null

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnLayoutShowRewarded.setOnClickListener {
                if (!SharedPreferences.isProApp(this@RewardedActivity)) {
                    showRewardedAd()
                } else {
                    eventAfterShowAd()
                }
            }

            btnItemShowRewarded.setOnClickListener {
                openActivity(RewardedAdsActivity::class.java)
            }
        }
    }

    private fun showRewardedAd() {
        showLoading()
        rewardedManager =
            RewardedManager.newInstance(this, getString(R.string.rewarded_ad_unit_id))
        rewardedManager?.loadAd(afterLoadAd = {
            hideLoading()
            rewardedManager?.showRewarded(afterShowAd = {
                eventAfterShowAd()
            })
        })
    }

    private fun eventAfterShowAd() {
        openActivity(NothingActivity::class.java)
    }
}