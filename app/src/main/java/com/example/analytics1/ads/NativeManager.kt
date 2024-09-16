package com.example.analytics1.ads

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.nativead.NativeAd

class NativeManager private constructor(
    private val activity: Activity,
    private val adUnitId: String
) {
    private var currentNativeAd: NativeAd? = null


    // Optional: Function to clean up resources.
    fun destroyNative() {
        currentNativeAd?.destroy()
        Log.d("scp", "Native destroyNative")
    }

    companion object {
        fun newInstance(activity: Activity, adUnitId: String): NativeManager {
            return NativeManager(activity, adUnitId)
        }
    }
}
