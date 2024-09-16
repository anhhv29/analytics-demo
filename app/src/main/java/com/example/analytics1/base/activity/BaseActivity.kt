package com.example.analytics1.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.analytics1.util.Constants.Language.Companion.ENGLISH
import com.example.analytics1.util.MyUtils.Companion.hideNavigationBar
import com.example.analytics1.util.MyUtils.Companion.transparentStatusBar
import com.example.analytics1.util.SharedPreferences
import com.example.analytics1.view.dialog.LoadingDialog
import java.util.Locale

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    abstract fun getActivityBinding(): VB
    private var loadingDialog: LoadingDialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding()
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

    protected fun showLoading() {
        val tag = LoadingDialog::class.java.simpleName
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        if (existingFragment == null || !existingFragment.isAdded) {
            loadingDialog = LoadingDialog.newInstance()
            loadingDialog?.show(supportFragmentManager, tag)
        }
    }

    protected fun hideLoading() {
        val tag = LoadingDialog::class.java.simpleName
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        if (existingFragment != null && existingFragment is DialogFragment) {
            existingFragment.dismissAllowingStateLoss()
        }
    }
}