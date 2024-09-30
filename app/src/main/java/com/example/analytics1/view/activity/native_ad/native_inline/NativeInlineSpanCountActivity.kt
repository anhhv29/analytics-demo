package com.example.analytics1.view.activity.native_ad.native_inline

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeInlineSpanCountBinding
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.model.data.ItemDemoProvider
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.view.activity.ResultActivity
import com.example.analytics1.view.adapter.NativeInlineSpanCountAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NativeInlineSpanCountActivity : BaseActivity<ActivityNativeInlineSpanCountBinding>() {
    override fun getActivityBinding() = ActivityNativeInlineSpanCountBinding.inflate(layoutInflater)
    private lateinit var mAdapter: NativeInlineSpanCountAdapter

    override fun initView() {
        super.initView()

        setupRecyclerView()
        loadData()

        mAdapter.setClickListener(object : NativeInlineSpanCountAdapter.ItemClickListener {
            override fun eventClick(itemDemoModel: ItemDemoModel) {
                eventAfterClickItem(itemDemoModel)
            }
        })
    }

    private fun setupRecyclerView() {
        mAdapter = NativeInlineSpanCountAdapter(this, mutableListOf())

        // GridLayoutManager setup with span lookup
        val mLayoutManager = GridLayoutManager(this, 3).apply {
            spanSizeLookup = createSpanSizeLookup()
        }

        binding.rcvNativeInlineDemo.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }

    private fun createSpanSizeLookup(): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mAdapter.getItemViewType(position) == NativeInlineSpanCountAdapter.VIEW_TYPE_AD) {
                    3 // Full span for ads
                } else {
                    1 // Single span for normal items
                }
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val dataItemDemo = withContext(Dispatchers.IO) {
                    ItemDemoProvider().getListNativeSpanCountDemo()
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