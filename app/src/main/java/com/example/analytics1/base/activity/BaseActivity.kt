package com.example.analytics1.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.analytics1.util.Constants.Language.Companion.ENGLISH
import com.example.analytics1.util.MyUtils.Companion.hideNavigationBar
import com.example.analytics1.util.MyUtils.Companion.transparentStatusBar
import com.example.analytics1.util.SharedPreferences
import java.util.Locale

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        transparentStatusBar()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        loadAds()
        preLoadData()
        initView()
        clickView()
        observerData()
    }

    open fun loadAds() {}
    open fun preLoadData() {}
    open fun initView() {}
    open fun clickView() {}
    open fun observerData() {}

    override fun onResume() {
        super.onResume()
        hideNavigationBar()
    }

    override fun attachBaseContext(base: Context) {
        var currentLanguage: String? = SharedPreferences.getLanguage(base)
        if (currentLanguage.isNullOrEmpty()) currentLanguage = ENGLISH
        val resources = base.resources
        val locale = Locale(currentLanguage)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        val base1 = base.createConfigurationContext(config)
        super.attachBaseContext(base1)
    }
}