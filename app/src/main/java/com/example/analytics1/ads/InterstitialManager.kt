package com.example.analytics1.ads

import android.app.Activity
import android.util.Log
import com.example.analytics1.util.Constants.RemoteConfig.Companion.KEY_INTERVAL_AD_FULL
import com.example.analytics1.util.Constants.RemoteConfig.Companion.lastShowAdFull
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig

class InterstitialManager private constructor(
    private val activity: Activity,
    private val adUnitId: String
) {
    private var interstitialAd: InterstitialAd? = null
    private var adIsLoading: Boolean = false

    fun loadAd(afterLoadAd: () -> Unit) {
        // Request a new ad if one isn't already loaded.
        if (adIsLoading || interstitialAd != null) {
            return
        }

        adIsLoading = true
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                    adIsLoading = false
                    val error =
                        "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
                    Log.d("scp", "Interstitial onAdFailedToLoad $error")
                    afterLoadAd.invoke()
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    adIsLoading = false
                    Log.d("scp", "Interstitial onAdLoaded")
                    afterLoadAd.invoke()
                }
            },
        )
    }

    // Show the ad if it's ready.
    fun showInterstitial(afterShowAd: () -> Unit) {
        interstitialAd?.let {
            it.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    Log.d("scp", "showInterstitial onAdClicked")
                }

                override fun onAdDismissedFullScreenContent() {
                    Log.d("scp", "showInterstitial onAdDismissedFullScreenContent")
                    interstitialAd = null
                    lastShowAdFull = System.currentTimeMillis()
                    afterShowAd.invoke()
                    loadAd(afterLoadAd = {})
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e("scp", "showInterstitial onAdFailedToShowFullScreenContent")
                    interstitialAd = null
                    lastShowAdFull = System.currentTimeMillis()
                    afterShowAd.invoke()
                    loadAd(afterLoadAd = {})
                }

                override fun onAdImpression() {
                    Log.d("scp", "showInterstitial onAdImpression")
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("scp", "showInterstitial onAdShowedFullScreenContent")
                }
            }
            it.show(activity)
        } ?: run {
            loadAd(afterLoadAd = {})
        }
    }


    // Show the interstitial ad with capping time
    private fun checkCappingTime(): Boolean {
        return (System.currentTimeMillis() - lastShowAdFull) > Firebase.remoteConfig.getValue(
            KEY_INTERVAL_AD_FULL
        ).asLong()
    }

    fun showInterstitialWithCappingTime(afterShowAd: () -> Unit) {
        if (interstitialAd == null) {
            loadAd(afterLoadAd = {})
            return
        }

        if (checkCappingTime()) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    Log.d("scp", "showInterstitialWithCappingTime onAdClicked")
                }

                override fun onAdDismissedFullScreenContent() {
                    Log.d("scp", "showInterstitialWithCappingTime onAdDismissedFullScreenContent")
                    interstitialAd = null
                    lastShowAdFull = System.currentTimeMillis()
                    afterShowAd.invoke()
                    loadAd(afterLoadAd = {})
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(
                        "scp",
                        "showInterstitialWithCappingTime onAdFailedToShowFullScreenContent"
                    )
                    interstitialAd = null
                    lastShowAdFull = System.currentTimeMillis()
                    afterShowAd.invoke()
                    loadAd(afterLoadAd = {})
                }

                override fun onAdImpression() {
                    Log.d("scp", "showInterstitialWithCappingTime onAdImpression")
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("scp", "showInterstitialWithCappingTime onAdShowedFullScreenContent")
                }
            }
            interstitialAd?.show(activity)
        } else {
            afterShowAd.invoke()
        }
    }

    companion object {
        fun newInstance(activity: Activity, adUnitId: String): InterstitialManager {
            return InterstitialManager(activity, adUnitId)
        }
    }
}