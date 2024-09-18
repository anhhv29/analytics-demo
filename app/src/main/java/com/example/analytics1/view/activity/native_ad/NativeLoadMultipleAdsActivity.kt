package com.example.analytics1.view.activity.native_ad

import android.annotation.SuppressLint
import android.util.Log
import com.example.analytics1.R
import com.example.analytics1.ads.NativeManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeLoadMutipleAdsBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.NothingActivity
import kotlin.random.Random

class NativeLoadMultipleAdsActivity : BaseActivity<ActivityNativeLoadMutipleAdsBinding>() {
    override fun getActivityBinding() = ActivityNativeLoadMutipleAdsBinding.inflate(layoutInflater)
    private var nativeManager: NativeManager? = null

    @SuppressLint("InflateParams")
    override fun loadAds() {
        super.loadAds()

        val defaultAdUnitId = getString(R.string.native_ad_unit_id)
        // Get the array of ad unit IDs
        val adUnitIds = resources.getStringArray(R.array.native_ad_unit_ids)
        // Get a random ad unit ID
        val randomAdUnitId = try {
            adUnitIds[Random.nextInt(adUnitIds.size)]
        } catch (e: Exception) {
            // Fallback to a known valid ad unit ID
            defaultAdUnitId
        }
        Log.d("scp", "Selected Ad Unit ID: $randomAdUnitId")

        nativeManager = NativeManager.newInstance(this, defaultAdUnitId)

        // Load multiple native ads (e.g., 3 ads)
        nativeManager?.loadMultipleAds(3) { nativeAds ->
            if (nativeAds.isNotEmpty()) {
                // Load ads into respective frames using the ad templates
                nativeManager?.loadAdTemplate(
                    nativeAds[0],
                    R.layout.gnt_medium_template_view,
                    binding.adFrame1
                )
                nativeManager?.loadAdTemplate(
                    nativeAds[1],
                    R.layout.gnt_small_template_view,
                    binding.adFrame2
                )
                nativeManager?.loadAdTemplate(
                    nativeAds[2],
                    R.layout.gnt_small_template_view,
                    binding.adFrame3
                )
            }
        }
    }

    override fun clickView() {
        super.clickView()
        binding.btnNothing.setOnClickListener {
            openActivity(NothingActivity::class.java)
        }
    }

    /** Called before the activity is destroyed. */
    public override fun onDestroy() {
        nativeManager?.destroyNative()
        super.onDestroy()
    }
}