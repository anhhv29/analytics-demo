package com.example.analytics1.ads

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.analytics1.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

class NativeManager private constructor(
    private val activity: Activity,
    private val adUnitId: String
) {
    private var nativeAd: NativeAd? = null
    private var adLoader: AdLoader? = null
    fun loadNativeAd(adLoadedCallback: (NativeAd?) -> Unit) {
        adLoader = AdLoader.Builder(activity, adUnitId)
            .forNativeAd { ad: NativeAd ->
                val isDestroyed = activity.isDestroyed
                if (isDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
                    ad.destroy()
                    return@forNativeAd
                }
                // Save the ad when successfully loaded
                // Destroy any previously loaded ad
                nativeAd?.destroy()
                nativeAd = ad
                adLoadedCallback(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                    Log.d("scp", "Native onAdClicked")
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                    Log.d("scp", "Native onAdClosed")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Code to be executed when an ad request fails.
                    Log.d("scp", "Native onAdFailedToLoad: ${loadAdError.message}")
                    adLoadedCallback(null)
                }

                override fun onAdImpression() {
                    // Code to be executed when an impression is recorded
                    // for an ad.
                    Log.d("scp", "Native onAdImpression")
                }

                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.d("scp", "Native onAdLoaded")
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.d("scp", "Native onAdOpened")
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setRequestMultipleImages(false)
                    .build()
            )
            .build()

        adLoader?.loadAd(AdRequest.Builder().build())
    }

    // Don't use the loadAds() method if you're using mediation,
    // as requests for multiple native ads don't currently work for ad unit IDs that have been configured for mediation.
    fun loadMultipleAds(adCount: Int, onAdsLoaded: (List<NativeAd>) -> Unit) {
        val adsList = mutableListOf<NativeAd>()
        adLoader = AdLoader.Builder(activity, adUnitId)
            .forNativeAd { ad: NativeAd ->
                adsList.add(ad)
                if (adsList.size == adCount) {
                    onAdsLoaded(adsList)
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                    Log.d("scp", "Native onAdClicked")
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                    Log.d("scp", "Native onAdClosed")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Code to be executed when an ad request fails.
                    Log.d("scp", "Native onAdFailedToLoad: ${loadAdError.message}")
                }

                override fun onAdImpression() {
                    // Code to be executed when an impression is recorded
                    // for an ad.
                    Log.d("scp", "Native onAdImpression")
                }

                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.d("scp", "Native onAdLoaded")
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.d("scp", "Native onAdOpened")
                }
            })
            .build()

        adLoader?.loadAds(AdRequest.Builder().build(), adCount)
    }

    fun loadAdTemplate(nativeAd: NativeAd, templateNativeAdResId: Int, layoutAds: FrameLayout) {
        // Inflate the layout using the provided layout resource ID
        val inflater = LayoutInflater.from(layoutAds.context)
        val templateNativeAd =
            inflater.inflate(templateNativeAdResId, null)

        // Find the NativeAdView inside the template
        val nativeAdView = templateNativeAd.findViewById<NativeAdView>(R.id.nativeAdView)

        // Bind the ad data to the layout
        bindNativeAd(nativeAdView, nativeAd)

        // Clear previous ad view from FrameLayout
        layoutAds.removeAllViews()

        // Add the new ad view to the FrameLayout
        layoutAds.addView(templateNativeAd)
    }

    private fun bindNativeAd(adView: NativeAdView, nativeAd: NativeAd) {
        // MediaView (video or large image)
        adView.mediaView = adView.findViewById(R.id.ad_media)
        adView.mediaView?.mediaContent = nativeAd.mediaContent

        // Headline (required)
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        (adView.headlineView as? TextView)?.text = nativeAd.headline

        // Body
        adView.bodyView = adView.findViewById(R.id.ad_body)
        if (nativeAd.body != null) {
            adView.bodyView?.visibility = View.VISIBLE
            (adView.bodyView as? TextView)?.text = nativeAd.body
        } else {
            adView.bodyView?.visibility = View.INVISIBLE
        }

        // Call to Action (required)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        (adView.callToActionView as? Button)?.text = nativeAd.callToAction

        // Icon
        adView.iconView = adView.findViewById(R.id.ad_icon)
        if (nativeAd.icon != null) {
            (adView.iconView as? ImageView)?.setImageDrawable(nativeAd.icon?.drawable)
            adView.iconView?.visibility = View.VISIBLE
        } else {
            adView.iconView?.visibility = View.GONE
        }

        // Price View
        adView.priceView = adView.findViewById(R.id.ad_price)
        if (nativeAd.price != null) {
            adView.priceView?.visibility = View.VISIBLE
            (adView.priceView as? TextView)?.text = nativeAd.price
        } else {
            adView.priceView?.visibility = View.GONE
        }

        // Star Rating View
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        if (nativeAd.starRating != null) {
            (adView.starRatingView as? RatingBar)?.rating = nativeAd.starRating?.toFloat() ?: 0f
            adView.starRatingView?.visibility = View.VISIBLE
        } else {
            adView.starRatingView?.visibility = View.GONE
        }

        // Store View
        adView.storeView = adView.findViewById(R.id.ad_store)
        if (nativeAd.store != null) {
            adView.storeView?.visibility = View.VISIBLE
            (adView.storeView as? TextView)?.text = nativeAd.store
        } else {
            adView.storeView?.visibility = View.GONE
        }

        // Advertiser View
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)
        if (nativeAd.advertiser != null) {
            adView.advertiserView?.visibility = View.VISIBLE
            (adView.advertiserView as? TextView)?.text = nativeAd.advertiser
        } else {
            adView.advertiserView?.visibility = View.GONE
        }

        // Assign the native ad to the native ad view
        adView.setNativeAd(nativeAd)

        //video controller
        val videoController = nativeAd.mediaContent?.videoController
        if (videoController?.hasVideoContent() == true) {
            videoController.videoLifecycleCallbacks =
                object : VideoController.VideoLifecycleCallbacks() {}
        }
    }

    // Optional: Function to clean up resources.
    fun destroyNative() {
        nativeAd?.let {
            it.destroy()
            nativeAd = null // Optionally, set to null after destruction
        }

        adLoader?.apply {
            if (isLoading) {
                // Cancel any ongoing ad loading operation
                // For AdLoader, there's no `cancel` method, so can just let the loader finish or handle cleanup separately
                Log.d("scp", "Native cancelling adLoader")
            }
        }
        Log.d("scp", "Native destroyNative")
    }

    companion object {
        fun newInstance(activity: Activity, adUnitId: String): NativeManager {
            return NativeManager(activity, adUnitId)
        }
    }
}
