package com.example.analytics1.view.activity

import com.bumptech.glide.Glide
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityResultBinding
import com.example.analytics1.model.ItemDemoModel
import com.google.gson.Gson

class ResultGsonActivity : BaseActivity<ActivityResultBinding>() {
    override fun getActivityBinding() = ActivityResultBinding.inflate(layoutInflater)

    private var dataText: String = ""
    private var dataImage: Int = 0
    override fun initView() {
        super.initView()

        val incomingJson = intent.getStringExtra("DATA")
        if (incomingJson != null) {
            val gson = Gson()

            val itemDemoModel = gson.fromJson(incomingJson, ItemDemoModel::class.java)
            dataText = itemDemoModel.id.toString()
            dataImage = itemDemoModel.image
        }

        binding.apply {
            tvResult.text = dataText
            Glide.with(this@ResultGsonActivity).load(dataImage).into(ivResult)
        }
    }
}