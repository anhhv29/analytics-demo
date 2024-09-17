package com.example.analytics1.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import com.example.analytics1.ads.AppOpenAdManager
import com.example.analytics1.ads.GoogleMobileAdsConsentManager
import com.example.analytics1.application.MyApplication
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivitySplashBinding
import com.example.analytics1.util.Constants.RemoteConfig.Companion.KEY_AD_OPEN_APP
import com.google.android.gms.ads.MobileAds
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
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
    private val gatherConsentFinished = AtomicBoolean(false)
    private var secondsRemaining: Long = 0L

    companion object {
        private const val COUNTER_TIME_MILLISECONDS = 12345L
    }

    override fun initView() {
        super.initView()

        // Log the Mobile Ads SDK version.
        Log.d("scp", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

        // Create a timer so the SplashActivity will be displayed for a fixed amount of time.
        createTimer()

        googleMobileAdsConsentManager =
            GoogleMobileAdsConsentManager.getInstance(applicationContext)
        googleMobileAdsConsentManager.gatherConsent(this) { consentError ->
            if (consentError != null) {
                // Consent not obtained in current session.
                Log.w("scp", String.format("%s: %s", consentError.errorCode, consentError.message))
            }

            gatherConsentFinished.set(true)

            if (googleMobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }

            if (secondsRemaining <= 0) {
                startMainActivity()
            }
        }

        // This sample attempts to load ads using consent obtained in the previous session.
        if (googleMobileAdsConsentManager.canRequestAds) {
            initializeMobileAdsSdk()
        }
    }

    /**
     * Create the countdown timer, which counts down to zero and show the app open ad.
     *
     * param time the number of milliseconds that the timer counts down from
     */
    private fun createTimer() {
        val countDownTimer: CountDownTimer =
            object : CountDownTimer(COUNTER_TIME_MILLISECONDS, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                }

                val showOpenAd = Firebase.remoteConfig.getBoolean(KEY_AD_OPEN_APP)
                override fun onFinish() {
                    secondsRemaining = 0
                    if (showOpenAd) {
                        (application as MyApplication).showAdIfAvailable(
                            this@SplashActivity,
                            object : AppOpenAdManager.OnShowOpenAdCompleteListener {
                                override fun onShowAdComplete() {
                                    // Check if the consent form is currently on screen before moving to the main
                                    // activity.
                                    if (gatherConsentFinished.get()) {
                                        startMainActivity()
                                    }
                                }
                            },
                        )
                    } else {
                        startMainActivity()
                    }
                }
            }
        countDownTimer.start()
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@SplashActivity) {}
            runOnUiThread {
                // Load an ad on the main thread.
                (application as MyApplication).loadAd(this@SplashActivity)
            }
        }
    }

    /** Start the MainActivity. */
    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
