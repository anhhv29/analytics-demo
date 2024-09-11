package com.example.analytics1.ads

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardedManager private constructor(
    private val activity: Activity,
    private val adUnitId: String
) {
    private var rewardedAd: RewardedAd? = null
    private var adIsLoading: Boolean = false
    fun loadAd(afterLoadAd: () -> Unit) {
        // Request a new ad if one isn't already loaded.
        if (adIsLoading || rewardedAd != null) {
            return
        }

        adIsLoading = true
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            activity,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                    adIsLoading = false
                    val error =
                        "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
                    Log.d("scp", "RewardedAd onAdFailedToLoad $error")
                    afterLoadAd.invoke()
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    adIsLoading = false
                    Log.d("scp", "RewardedAd onAdLoaded")
                    afterLoadAd.invoke()
                }
            },
        )
    }

    // Show the ad if it's ready.
    fun showRewarded(afterShowAd: () -> Unit) {
        rewardedAd?.let {
            it.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    Log.d("scp", "showRewardedAd onAdClicked")
                }

                override fun onAdDismissedFullScreenContent() {
                    Log.d("scp", "showRewardedAd onAdDismissedFullScreenContent")
                    rewardedAd = null
                    afterShowAd.invoke()
                    AppOpenAdManager.adFullShowing = false
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e("scp", "showRewardedAd onAdFailedToShowFullScreenContent")
                    rewardedAd = null
                    afterShowAd.invoke()
                    AppOpenAdManager.adFullShowing = false
                }

                override fun onAdImpression() {
                    Log.d("scp", "showRewardedAd onAdImpression")
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("scp", "showRewardedAd onAdShowedFullScreenContent")
                    AppOpenAdManager.adFullShowing = true
                }
            }
            it.show(activity) {}
        }
    }

    companion object {
        fun newInstance(activity: Activity, adUnitId: String): RewardedManager {
            return RewardedManager(activity, adUnitId)
        }
    }
}