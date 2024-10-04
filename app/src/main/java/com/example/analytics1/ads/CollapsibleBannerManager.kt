package com.example.analytics1.ads

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowMetrics
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class CollapsibleBannerManager private constructor(
    private val activity: Activity,
    private val adUnitId: String
) {
    private var adView: AdView? = null

    // Calculate the ad size based on screen width.
    private val mAdSize: AdSize
        get() {
            val displayMetrics = activity.resources.displayMetrics
            val adWidthPixels = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
                windowMetrics.bounds.width()
            } else {
                displayMetrics.widthPixels
            }
            val density = displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
        }

    // Initialize and load banner ad
    fun loadCollapsibleBanner(adContainer: ViewGroup, afterLoadAd: () -> Unit) {
        adView = AdView(activity).apply {
            adUnitId = this@CollapsibleBannerManager.adUnitId
            setAdSize(mAdSize)
        }

        // Create an extra parameter that aligns the bottom of the expanded ad to
        // the bottom of the bannerView.
        val extras = Bundle()
        extras.putString("collapsible", "bottom")

        // Replace the ad container with the new ad view.
        adContainer.removeAllViews()
        adContainer.addView(adView)

        // Listen to ad events.
        adListener(afterLoadAd)

        // Load the ad.
        val adRequest = AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            .build()
        adView?.loadAd(adRequest)
    }

    // Optional: Function to clean up resources.
    fun destroyBanner() {
        adView?.destroy()
        Log.d("scp", "Collapsible destroyBanner")
    }

    fun pauseBanner() {
        adView?.pause()
        Log.d("scp", "Collapsible pauseBanner")
    }

    fun resumeBanner() {
        adView?.resume()
        Log.d("scp", "Collapsible resumeBanner")
    }

    private fun adListener(afterLoadAd: () -> Unit) {
        adView?.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("scp", "CollapsibleBanner onAdClicked")
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d("scp", "CollapsibleBanner onAdClosed")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.d("scp", "CollapsibleBanner onAdFailedToLoad")
                afterLoadAd.invoke()
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                Log.d("scp", "CollapsibleBanner onAdImpression")
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("scp", "CollapsibleBanner onAdLoaded")
                afterLoadAd.invoke()
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("scp", "CollapsibleBanner onAdOpened")
            }
        }
    }

    companion object {
        fun newInstance(activity: Activity, adUnitId: String): CollapsibleBannerManager {
            return CollapsibleBannerManager(activity, adUnitId)
        }
    }
}