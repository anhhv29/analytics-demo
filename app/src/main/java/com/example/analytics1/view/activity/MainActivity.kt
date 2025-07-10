package com.example.analytics1.view.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.analytics1.R
import com.example.analytics1.base.activity.BaseActivity
import com.example.analytics1.databinding.ActivityMainBinding
import com.example.analytics1.util.MyUtils.Companion.openActivity
import com.example.analytics1.util.MyUtils.Companion.showToast

/**
 * @Author: anhhv
 * @Date: 12,June,2024.
 * @Accounts
 *      -> https://github.com/...
 */

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getActivityBinding() = ActivityMainBinding.inflate(layoutInflater)

    companion object {
        const val CHANNEL_ID_FIREBASE = "firebase_channel"
        const val CHANNEL_ID_ADMOB = "admob_channel"
    }

    override fun initView() {
        super.initView()
        createNotificationChannels()
        askNotificationPermission()
    }

    override fun clickView() {
        super.clickView()

        // Override the default implementation when the user presses the back key.
        val onBackPressedCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    moveTaskToBack(true)
                }
            }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.btnFirebase.setOnClickListener {
            openActivity(FirebaseActivity::class.java)
        }

        binding.btnAdMob.setOnClickListener {
            openActivity(AdmobActivity::class.java)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            showToast(getString(R.string.notification_access_granted))
        } else {
            showToast(getString(R.string.notification_access_denied))
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API Level > 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
                Log.d("scp", "Notification Access Granted")
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val firebaseChannel = NotificationChannel(
                CHANNEL_ID_FIREBASE,
                "Firebase",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Thông báo từ Firebase"
            }

            val admobChannel = NotificationChannel(
                CHANNEL_ID_ADMOB,
                "AdMob",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Thông báo từ AdMob"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(firebaseChannel)
            notificationManager?.createNotificationChannel(admobChannel)
        }
    }

    private fun sendFirebaseNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_FIREBASE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Firebase")
            .setContentText("Đây là thông báo từ Firebase")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(1001, notification)
    }

    private fun sendAdmobNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID_ADMOB)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("AdMob")
            .setContentText("Đây là thông báo từ AdMob")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat.from(this).notify(1002, notification)
    }
}