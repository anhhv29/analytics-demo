package com.example.analytics1.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.Date

class AppOpenAdManager private constructor(
    private val adUnitId: String
) {
    private var appOpenAd: AppOpenAd? = null
    var isLoadingAd = false
    var isShowingAd = false
    var isShowAppOpenAdComplete = false

    /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
    private var loadTime: Long = 0

    /**
     * Load an ad.
     *
     * param context the context of the activity that loads the ad
     */
    fun loadAd(context: Context, afterLoadAd: () -> Unit) {
        // Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAd || isAdAvailable()) {
            return
        }

        isLoadingAd = true

        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            adUnitId,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                /**
                 * Called when an app open ad has loaded.
                 *
                 * param ad the loaded app open ad.
                 */
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                    afterLoadAd.invoke()
                    Log.d("scp", "AppOpenAd onAdLoaded.")
                }

                /**
                 * Called when an app open ad has failed to load.
                 *
                 * param loadAdError the error.
                 */
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    appOpenAd = null
                    isLoadingAd = false
                    afterLoadAd.invoke()
                    Log.d("scp", "AppOpenAd onAdFailedToLoad: " + loadAdError.message)
                }
            }
        )
    }

    /** Check if ad was loaded more than n hours ago. */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference: Long = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }

    /** Check if ad exists and can be shown. */
    private fun isAdAvailable(): Boolean {
        // Ad references in the app open beta will time out after four hours, but this time limit
        // may change in future beta versions. For details, see:
        // https://support.google.com/admob/answer/9341964?hl=en
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }

    /**
     * Show the ad if one isn't already showing.
     *
     * param activity the activity that shows the app open ad
     */
    fun showAdIfAvailable(activity: Activity) {
        showAdIfAvailable(
            activity,
            object : OnShowOpenAdCompleteListener {
                override fun onShowAdComplete(done: Boolean) {
                    // Empty because the user will go back to the activity that shows the ad.
                    isShowAppOpenAdComplete = true
                }
            }
        )
    }

    /**
     * Show the ad if one isn't already showing.
     *
     * param activity the activity that shows the app open ad
     * param onShowAdCompleteListener the listener to be notified when an app open ad is complete
     */
    fun showAdIfAvailable(
        activity: Activity,
        onShowAdCompleteListener: OnShowOpenAdCompleteListener
    ) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d("scp", "The app open ad is already showing.")
            return
        }

        // If the app open ad is not available yet, invoke the callback.
        if (!isAdAvailable()) {
            Log.d("scp", "The app open ad is not ready yet.")
            onShowAdCompleteListener.onShowAdComplete(true)
            loadAd(activity) {}
            return
        }

        Log.d("scp", "Will show ad.")

        appOpenAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                /** Called when full screen content is dismissed. */
                override fun onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    appOpenAd = null
                    isShowingAd = false
                    Log.d("scp", "onAdDismissedFullScreenContent.")
                    onShowAdCompleteListener.onShowAdComplete(true)
                    loadAd(activity) {}
                }

                /** Called when fullscreen content failed to show. */
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    appOpenAd = null
                    isShowingAd = false
                    Log.d("scp", "onAdFailedToShowFullScreenContent: " + adError.message)
                    onShowAdCompleteListener.onShowAdComplete(true)
                    loadAd(activity) {}
                }

                /** Called when fullscreen content is shown. */
                override fun onAdShowedFullScreenContent() {
                    Log.d("scp", "onAdShowedFullScreenContent.")
                    onShowAdCompleteListener.onShowAdComplete(false)
                }
            }
        isShowingAd = true
        appOpenAd?.show(activity)
    }

    /**
     * Interface definition for a callback to be invoked when an app open ad is complete (i.e.
     * dismissed or fails to show).
     */
    interface OnShowOpenAdCompleteListener {
        fun onShowAdComplete(done: Boolean)
    }

    companion object {
        fun newInstance(adUnitId: String): AppOpenAdManager {
            return AppOpenAdManager(adUnitId)
        }

        //check Interstitial or Rewarded show
        var adFullShowing = false
    }
}