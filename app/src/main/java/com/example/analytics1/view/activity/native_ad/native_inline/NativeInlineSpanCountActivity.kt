package com.example.analytics1.view.activity.native_ad.native_inline

import androidx.recyclerview.widget.GridLayoutManager
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeInlineSpanCountBinding
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.model.data.ItemDemoProvider
import com.example.analytics1.view.adapter.NativeInlineSpanCountAdapter

class NativeInlineSpanCountActivity : BaseActivity<ActivityNativeInlineSpanCountBinding>() {
    override fun getActivityBinding() = ActivityNativeInlineSpanCountBinding.inflate(layoutInflater)
    private var mAdapter: NativeInlineSpanCountAdapter? = null

    override fun initView() {
        super.initView()
        val dataItemDemo = ItemDemoProvider().getListNativeSpanCountDemo()
        mAdapter = NativeInlineSpanCountAdapter(this, dataItemDemo)

        mAdapter?.setClickListener(object : NativeInlineSpanCountAdapter.ItemClickListener {
            override fun eventClick(itemDemoModel: ItemDemoModel) {

            }
        })

        val mLayoutManager = GridLayoutManager(this, 2)

        binding.rcvNativeInlineDemo.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }
}