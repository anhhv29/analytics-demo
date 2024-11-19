package com.example.analytics1.view.activity.interstitial_ad

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.analytics1.R
import com.example.analytics1.ads.InterstitialManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityInterstitialAdsBinding
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.model.data.ItemDemoProvider
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.util.SharedPreferences
import com.example.analytics1.view.activity.ResultActivity
import com.example.analytics1.view.activity.ResultGsonActivity
import com.example.analytics1.view.adapter.InterstitialDemoAdapter
import com.google.gson.Gson

class InterstitialAdsActivity : BaseActivity<ActivityInterstitialAdsBinding>() {
    override fun getActivityBinding() = ActivityInterstitialAdsBinding.inflate(layoutInflater)

    private var interstitialManager: InterstitialManager? = null
    private var mAdapter: InterstitialDemoAdapter? = null
    override fun loadAds() {
        super.loadAds()
        if (!SharedPreferences.isProApp(this)) {
            showLoading()
            interstitialManager =
                InterstitialManager.newInstance(
                    this,
                    getString(R.string.interstitial_video_ad_unit_id)
                )
            interstitialManager?.loadAd(afterLoadAd = {
                hideLoading()
            })
        }
    }

    override fun initView() {
        super.initView()

        val dataItemDemo = ItemDemoProvider().getListInterstitialDemo()
        mAdapter = InterstitialDemoAdapter(this, dataItemDemo)
        mAdapter?.setClickListener(object : InterstitialDemoAdapter.ItemClickListener {
            override fun eventClick(itemDemoModel: ItemDemoModel) {
                eventClickItem(itemDemoModel)
            }
        })

        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rcvInterstitialDemo.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun eventClickItem(itemDemoModel: ItemDemoModel) {
        if (!SharedPreferences.isProApp(this)) {
            interstitialManager?.showInterstitial(afterShowAd = {
                eventAfterClickItem(itemDemoModel)
            })
        } else {
            //premium
            eventAfterClickItem(itemDemoModel)
        }
    }

    private fun eventAfterClickItem(itemDemoModel: ItemDemoModel) {
        val gson = Gson()
        val passableObject = gson.toJson(itemDemoModel)
        openActivity(ResultGsonActivity::class.java) {
            putString("DATA", passableObject)
        }
    }
}