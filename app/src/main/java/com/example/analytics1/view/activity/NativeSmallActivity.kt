package com.example.analytics1.view.activity

import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNativeSmallBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity

class NativeSmallActivity : BaseActivity<ActivityNativeSmallBinding>() {
    override fun getActivityBinding() = ActivityNativeSmallBinding.inflate(layoutInflater)

    override fun clickView() {
        super.clickView()
        binding.apply {
            btnNativeSmallOld.setOnClickListener {
                openActivity(NativeSmallOldActivity::class.java)
            }

            btnNativeSmallGnt.setOnClickListener {
                openActivity(NativeSmallGntActivity::class.java)
            }
        }
    }
}