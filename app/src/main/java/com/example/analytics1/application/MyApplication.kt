package com.example.analytics1.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.example.analytics1.R
import com.example.analytics1.ads.AppOpenAdManager
import com.example.analytics1.util.SharedPreferences
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

/** Application class that initializes, loads and show ads when activities change states. */
class MyApplication :
    MultiDexApplication(), Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {

    private var appOpenAdManager: AppOpenAdManager? = null
    private var currentActivity: Activity? = null

    override fun onCreate() {
        super<MultiDexApplication>.onCreate()
        registerActivityLifecycleCallbacks(this)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        appOpenAdManager =
            AppOpenAdManager.newInstance(getString(R.string.open_ad_unit_id))
    }

    /**
     * DefaultLifecycleObserver method that shows the app open ad when the app moves to foreground.
     */
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        // If the ad Interstitial or Rewarded is already showing, do not show the ad AppOpenAd.
        if (AppOpenAdManager.adFullShowing) {
            Log.d("scp", "The app open ad is adFullShowing.")
            return
        }

        currentActivity?.let {
            remoteConfigs()
            // Show the ad (if available) when the app moves to foreground.
            if (!SharedPreferences.isProApp(it)) {
                appOpenAdManager?.showAdIfAvailable(it)
            }
        }
    }

    //firebase remote config
    private fun remoteConfigs() {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task: Task<Boolean> ->
                if (task.isSuccessful) {
                    Log.d("scp", "Remote Config params updated")
                } else {
                    Log.d("scp", "Failed to update Remote Config params")
                }
            }
    }

    /** ActivityLifecycleCallback methods. */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        // An ad activity is started when an ad is showing, which could be AdActivity class from Google
        // SDK or another activity class implemented by a third party mediation partner. Updating the
        // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
        // one that shows the ad.
        if (appOpenAdManager?.isShowingAd == false) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    companion object {
        const val TEST_DEVICE_HASHED_ID = "ABCDEF012345"
    }
}
