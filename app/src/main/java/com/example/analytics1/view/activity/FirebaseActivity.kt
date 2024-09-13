package com.example.analytics1.view.activity

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import com.example.analytics1.R
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityFirebaseBinding
import com.example.analytics1.util.Constants
import com.example.analytics1.util.Constants.LogEvents.Companion.EVENT_CLICK_IMAGE
import com.example.analytics1.util.Constants.LogEvents.Companion.EVENT_CLICK_TEXT
import com.example.analytics1.util.Constants.LogEvents.Companion.EVENT_CLICK_VOICE
import com.example.analytics1.util.MyFirebaseMessagingService.Companion.getTokenFCM
import com.example.analytics1.util.MyUtils.Companion.showToast
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.remoteconfig.remoteConfig

class FirebaseActivity : BaseActivity<ActivityFirebaseBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityFirebaseBinding =
        ActivityFirebaseBinding.inflate(layoutInflater)

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var text = ""

    override fun clickView() {
        super.clickView()
        //firebase analytics
        firebaseAnalytics = Firebase.analytics
        binding.apply {
            //firebase log event
            vText.setOnClickListener {
                firebaseAnalytics.logEvent(EVENT_CLICK_TEXT) {
                    showToast(EVENT_CLICK_TEXT)
                    Log.d("scp", EVENT_CLICK_TEXT)
                }
            }
            vImage.setOnClickListener {
                firebaseAnalytics.logEvent(EVENT_CLICK_IMAGE) {
                    showToast(EVENT_CLICK_IMAGE)
                    Log.d("scp", EVENT_CLICK_IMAGE)
                }
            }
            vVoice.setOnClickListener {
                firebaseAnalytics.logEvent(EVENT_CLICK_VOICE) {
                    showToast(EVENT_CLICK_VOICE)
                    Log.d("scp", EVENT_CLICK_VOICE)
                }
            }

            //firebase crashlytics
            btnCrash.setOnClickListener {
                if (checkBoxCrash.isChecked) {
                    try {
                        Log.d("crash", "crash1")
                        throw NullPointerException()
                    } catch (ex: NullPointerException) {
                        Log.d("crash", "no crash")
                    }
                } else {
                    Log.d("crash", "crash2")
                    throw NullPointerException()
                }
            }

            //firebase realtime database
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("data")
            // Write a message to the database
            btnWrite.setOnClickListener {
                val str = edtRealtimeDb.text.toString()
                reference.setValue(str)
            }
            // Read from the database
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    text = dataSnapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("scp", "DatabaseError")
                }
            })
            btnRead.setOnClickListener {
                tvRealtimeDb.text = text
            }

            //remote config
            val showImage: Boolean =
                Firebase.remoteConfig.getBoolean(Constants.RemoteConfig.SHOW_BACKGROUND_IMAGE)
            if (showImage) {
                binding.ivRemote.setBackgroundResource(R.drawable.ic_firebase_lockup)
            } else binding.ivRemote.setBackgroundColor(Color.TRANSPARENT)

            //Log token to test send notification for this device
            binding.btnLogToken.setOnClickListener {
                // Get token
                getTokenFCM()
            }
        }
    }
}