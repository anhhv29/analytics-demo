package com.example.analytics1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.analytics1.databinding.ActivityAdmobBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdMobActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AdMobActivity"
        private const val TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713"
    }

    private lateinit var binding: ActivityAdmobBinding
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIds()
        // Initialize the Google Mobile Ads SDK
        MobileAds.initialize(this)

        requestNewInterstitial()

        binding.btnLoadInterstitial.setOnClickListener {
            if (interstitialAd != null) {
                interstitialAd?.show(this)
            } else {
                goToHome()
            }
        }

        // Disable button if an interstitial ad is not loaded yet.
        binding.btnLoadInterstitial.isEnabled = interstitialAd != null
    }

    //    Called when leaving the activity
    override fun onPause() {
        binding.adView.pause()
        super.onPause()
    }

    //    Called when returning to the activity
    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        if (interstitialAd == null) {
            requestNewInterstitial()
        }
    }

    //    Called before the activity is destroyed
    override fun onDestroy() {
        binding.adView.destroy()
        super.onDestroy()
    }

    private fun requestNewInterstitial() {
        // AdMob ad unit IDs are not currently stored inside the google-services.json file.
        // Developers using AdMob can store them as custom values in a string resource file or
        // simply use constants. Note that the ad units used here are configured to return only test
        // ads, and should not be used outside this sample.
        val adRequest = AdRequest.Builder().build()

        binding.adView.loadAd(adRequest)

        InterstitialAd.load(
            this,
            getString(R.string.interstitial_ad_unit_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    interstitialAd = ad
                    // Ad received, ready to display
                    binding.btnLoadInterstitial.isEnabled = true

                    interstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                goToHome()
                            }
                        }
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    interstitialAd = null
                    Log.w(TAG, "onAdFailedToLoad:${error.message}")
                }
            },
        )
    }

    private fun goToHome() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkIds() {
        if (TEST_APP_ID == getString(R.string.admob_app_id)) {
            Log.w(TAG, "Your admob_app_id is not configured correctly, please see the README")
        }
    }
}