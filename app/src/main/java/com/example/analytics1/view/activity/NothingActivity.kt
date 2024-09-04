package com.example.analytics1.view.activity

import android.view.LayoutInflater
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityNothingBinding

class NothingActivity : BaseActivity<ActivityNothingBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityNothingBinding =
        ActivityNothingBinding.inflate(layoutInflater)
}