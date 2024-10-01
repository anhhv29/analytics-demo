package com.example.analytics1.view.activity

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import com.example.analytics1.R
import com.example.analytics1.ads.AppOpenAdManager
import com.example.analytics1.ads.GoogleMobileAdsConsentManager
import com.example.analytics1.application.MyApplication.Companion.TEST_DEVICE_HASHED_ID
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivitySplashBinding
import com.example.analytics1.util.Constants.RemoteConfig.Companion.KEY_AD_OPEN_APP
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.util.SharedPreferences
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

/** Splash Activity that inflates splash activity xml. */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getActivityBinding() = ActivitySplashBinding.inflate(layoutInflater)

    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    private var appOpenAdManager: AppOpenAdManager? = null
    private var countDownTimer: CountDownTimer? = null
    private var secondsRemaining: Long = 0L
    private var finishedTimer = false

    override fun initView() {
        super.initView()

        // Log the Mobile Ads SDK version.
        Log.d("scp", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

        if (!SharedPreferences.isProApp(this)) {
            googleMobileAdsConsentManager =
                GoogleMobileAdsConsentManager.getInstance(applicationContext)
            googleMobileAdsConsentManager.gatherConsent(this) { consentError ->
                consentError?.let {
                    Log.w("scp", "${it.errorCode}: ${it.message}")
                    continueApp()
                    return@gatherConsent
                }

                if (googleMobileAdsConsentManager.canRequestAds) {
                    initializeMobileAdsSdk()
                } else {
                    continueApp()
                }
            }
        } else {
            continueApp()
        }
    }

    private fun createTimer() {
        countDownTimer = object : CountDownTimer(12345, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
            }

            override fun onFinish() {
                Log.d("scp", "createTimer: onFinish")
                finishedTimer = true
                secondsRemaining = 0
                continueApp()
            }
        }
        countDownTimer?.start()
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) return

        // Set your test devices.
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(TEST_DEVICE_HASHED_ID))
                .build()
        )

        createTimer()

        val showOpenAd = FirebaseRemoteConfig.getInstance().getBoolean(KEY_AD_OPEN_APP)
        if (showOpenAd) {
            CoroutineScope(Dispatchers.IO).launch {
                // Initialize the Google Mobile Ads SDK on a background thread.
                MobileAds.initialize(this@SplashActivity) {}
                runOnUiThread {
                    // Load an ad on the main thread.
                    appOpenAdManager =
                        AppOpenAdManager.newInstance(getString(R.string.open_ad_unit_id))
                    appOpenAdManager?.loadAd(this@SplashActivity) {
                        appOpenAdManager?.isLoadingAd = true
                        if (!finishedTimer) {
                            appOpenAdManager?.showAdIfAvailable(
                                this@SplashActivity,
                                object : AppOpenAdManager.OnShowOpenAdCompleteListener {
                                    override fun onShowAdComplete(done: Boolean) {
                                        countDownTimer?.cancel()
                                        if (done) {
                                            continueApp()
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        } else {
            continueApp()
        }
    }

    /** Start the MainActivity. */
    fun continueApp() {
        openActivity(MainActivity::class.java)
    }
}
