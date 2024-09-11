package com.example.analytics1.view.activity

import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.example.analytics1.R
import com.example.analytics1.ads.RewardedManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityRewardedAdsBinding
import com.example.analytics1.enums.TypeData
import com.example.analytics1.interfaces.ItemRewardedClickListener
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.model.data.ItemDemoProvider
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.util.SharedPreferences
import com.example.analytics1.view.adapter.RewardedDemoAdapter

class RewardedAdsActivity : BaseActivity<ActivityRewardedAdsBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityRewardedAdsBinding =
        ActivityRewardedAdsBinding.inflate(layoutInflater)

    private var rewardedManager: RewardedManager? = null
    private var mAdapter: RewardedDemoAdapter? = null
    override fun initView() {
        super.initView()

        val dataItemDemo = ItemDemoProvider().getListRewardedDemo()
        mAdapter = RewardedDemoAdapter(this, dataItemDemo, object : ItemRewardedClickListener {
            override fun eventClick(itemDemoModel: ItemDemoModel) {
                eventClickItem(itemDemoModel)
            }
        })

        val mLayoutManager = GridLayoutManager(this, 1)

        binding.rcvRewardedDemo.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun eventClickItem(itemDemoModel: ItemDemoModel) {
        if (!SharedPreferences.isProApp(this)) {
            if (itemDemoModel.type == TypeData.ADS.name) {
                showRewardedAd(itemDemoModel)
            } else {
                eventAfterClickItem(itemDemoModel)
            }
        } else {
            //premium
            eventAfterClickItem(itemDemoModel)
        }
    }

    private fun showRewardedAd(itemDemoModel: ItemDemoModel) {
        showLoading()
        rewardedManager =
            RewardedManager.newInstance(this, getString(R.string.rewarded_ad_unit_id))
        rewardedManager?.loadAd(afterLoadAd = {
            hideLoading()
            rewardedManager?.showRewarded(afterShowAd = {
                eventAfterClickItem(itemDemoModel)
            })
        })
    }

    private fun eventAfterClickItem(itemDemoModel: ItemDemoModel) {
        openActivity(ResultActivity::class.java) {
            putString("DATA_TEXT", itemDemoModel.id.toString())
            putInt("DATA_IMAGE", itemDemoModel.image)
        }
    }
}