package com.example.analytics1.view.activity

import com.bumptech.glide.Glide
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityResultBinding

class ResultActivity : BaseActivity<ActivityResultBinding>() {
    override fun getActivityBinding() = ActivityResultBinding.inflate(layoutInflater)

    private var dataText: String = ""
    private var dataImage: Int = 0
    override fun initView() {
        super.initView()
        if (intent != null) {
            dataText = intent.getStringExtra("DATA_TEXT").toString()
            dataImage = intent.getIntExtra("DATA_IMAGE", 0)
        }

        binding.apply {
            tvResult.text = dataText
            Glide.with(this@ResultActivity).load(dataImage).into(ivResult)
        }
    }
}