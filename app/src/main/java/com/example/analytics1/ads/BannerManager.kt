package com.example.analytics1.ads

import android.app.Activity
import android.os.Build
import android.view.ViewGroup
import android.view.WindowMetrics
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class BannerManager private constructor(
    private val activity: Activity,
    private val adUnitId: String
) {

    private lateinit var adView: AdView

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
    fun loadBanner(adContainer: ViewGroup) {
        adView = AdView(activity).apply {
            adUnitId = this@BannerManager.adUnitId
            setAdSize(mAdSize)
        }

        // Replace the ad container with the new ad view.
        adContainer.removeAllViews()
        adContainer.addView(adView)

        // Load the ad.
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    // Optional: Function to clean up resources.
    fun destroyBanner() {
        adView.destroy()
    }

    fun pauseBanner() {
        adView.pause()
    }

    fun resumeBanner() {
        adView.resume()
    }

    companion object {
        fun newInstance(activity: Activity, adUnitId: String): BannerManager {
            return BannerManager(activity, adUnitId)
        }
    }
}
