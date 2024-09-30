package com.example.analytics1.view.activity.native_ad.native_inline

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeInlineAdsBinding
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.model.data.ItemDemoProvider
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.ResultActivity
import com.example.analytics1.view.adapter.NativeInlineAdsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NativeInlineAdsActivity : BaseActivity<ActivityNativeInlineAdsBinding>() {
    override fun getActivityBinding() = ActivityNativeInlineAdsBinding.inflate(layoutInflater)
    private lateinit var mAdapter: NativeInlineAdsAdapter

    override fun initView() {
        super.initView()

        setupRecyclerView()
        loadData()

        mAdapter.setClickListener(object : NativeInlineAdsAdapter.ItemClickListener {
            override fun eventClick(itemDemoModel: ItemDemoModel) {
                eventAfterClickItem(itemDemoModel)
            }
        })
    }

    private fun setupRecyclerView() {
        mAdapter = NativeInlineAdsAdapter(this, mutableListOf())

        // Setting up LinearLayoutManager for vertical scrolling
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvNativeInlineDemo.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val dataItemDemo = withContext(Dispatchers.IO) {
                    ItemDemoProvider().getListNativeInlineDemo()
                }
                mAdapter.updateData(dataItemDemo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun eventAfterClickItem(itemDemoModel: ItemDemoModel) {
        openActivity(ResultActivity::class.java) {
            putString("DATA_TEXT", itemDemoModel.id.toString())
            putInt("DATA_IMAGE", itemDemoModel.image)
        }
    }
}