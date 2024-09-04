package com.example.analytics1.view.activity

import android.view.LayoutInflater
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityAdmobBinding

class AdmobActivity : BaseActivity<ActivityAdmobBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAdmobBinding =
        ActivityAdmobBinding.inflate(layoutInflater)

}