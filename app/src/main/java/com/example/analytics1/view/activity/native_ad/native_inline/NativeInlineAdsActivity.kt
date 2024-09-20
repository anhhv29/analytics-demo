package com.example.analytics1.view.activity.native_ad.native_inline

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeInlineAdsBinding
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.model.data.ItemDemoProvider
import com.example.analytics1.view.adapter.NativeInlineAdsAdapter

class NativeInlineAdsActivity : BaseActivity<ActivityNativeInlineAdsBinding>() {
    override fun getActivityBinding() = ActivityNativeInlineAdsBinding.inflate(layoutInflater)
    private var mAdapter: NativeInlineAdsAdapter? = null
    override fun initView() {
        super.initView()
        val dataItemDemo = ItemDemoProvider().getListNativeInlineDemo()
        mAdapter = NativeInlineAdsAdapter(this, dataItemDemo)

        mAdapter?.setClickListener(object : NativeInlineAdsAdapter.ItemClickListener {
            override fun eventClick(itemDemoModel: ItemDemoModel) {

            }
        })

        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rcvNativeInlineDemo.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }
}