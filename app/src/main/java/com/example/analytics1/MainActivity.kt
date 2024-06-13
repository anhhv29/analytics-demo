package com.example.analytics1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.analytics1.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.messaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

/**
 * @Author: anhhv
 * @Date: 12,June,2024.
 * @Accounts
 *      -> https://github.com/...
 */

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                this,
                "FCM can't post notifications without POST_NOTIFICATIONS permission",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
//    private lateinit var crashlytics: FirebaseCrashlytics

    companion object {
        private const val TEXT = "text"
        private const val IMAGE = "image"
        private const val VOICE = "voice"
        private const val TAG = "Main"
        private const val SHOW_BACKGROUND_IMAGE = "show_image"
    }

    private var text = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase analytics
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics
        var type: String
        binding.apply {
            vText.setOnClickListener {
                type = TEXT
                firebaseAnalytics.logEvent(type) {
                    //            param("type", type)
                    Toast.makeText(baseContext, type, Toast.LENGTH_SHORT).show()
                    Log.d("logEven", type)
                }
            }
            vImage.setOnClickListener {
                type = IMAGE
                firebaseAnalytics.logEvent(type) {
                    //            param("type", type)
                    Toast.makeText(baseContext, type, Toast.LENGTH_SHORT).show()
                    Log.d("logEven", type)
                }
            }
            vVoice.setOnClickListener {
                type = VOICE
                firebaseAnalytics.logEvent(type) {
                    //            param("type", type)
                    Toast.makeText(baseContext, type, Toast.LENGTH_SHORT).show()
                    Log.d("logEven", type)
                }
            }
        }

        //firebase crashlytics
//        crashlytics = Firebase.crashlytics
//        Log the onCreate event, this will also be printed in logcat
//        crashlytics.log("onCreate")
        binding.apply {
            btnCrash.setOnClickListener {
//                Log that crash button was clicked.
//                crashlytics.log("Crash button clicked")
                if (checkBoxCrash.isChecked) {
                    try {
                        Log.d("crash", "crash1")
                        throw NullPointerException()
                    } catch (ex: NullPointerException) {
//                         [START crashlytics_log_and_report]
                        Log.d("crash", "no crash")
//                        crashlytics.log("NPE caught!")
//                        crashlytics.recordException(ex)
//                         [END crashlytics_log_and_report]
                    }
                } else {
                    Log.d("crash", "crash2")
                    throw NullPointerException()
                }
            }
        }

        //firebase remote config
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Remote Config params updated")
                } else {
                    Log.d(TAG, "Failed to update Remote Config params")
                }
            }

        //firebase realtime database
        binding.apply {
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
                    Log.d(TAG, "DatabaseError")
                }
            })

            btnRead.setOnClickListener {
                tvRealtimeDb.text = text
            }
        }

        //firebase cloud messaging
        askNotificationPermission()
        //Log token to test send notification for this device
        binding.btnLogToken.setOnClickListener {
            // Get token
            // [START log_reg_token]
            Firebase.messaging.token.addOnCompleteListener(
                OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                    Log.d(TAG, msg)
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                },
            )
            // [END log_reg_token]
        }

        //firebase google admob
        binding.btnAdMob.setOnClickListener {
            intent = Intent(this, AdMobActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        //remote config
        val showImage: Boolean = Firebase.remoteConfig.getBoolean(SHOW_BACKGROUND_IMAGE)
        if (showImage) {
            binding.ivRemote.setBackgroundResource(R.drawable.firebase_lockup)
        } else binding.ivRemote.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun askNotificationPermission() {
        // This is only necessary for API Level > 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}