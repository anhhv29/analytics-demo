package com.example.analytics1.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat

class MyUtils {
    companion object {
        fun View.goneView() {
            visibility = View.GONE
        }

        fun View.visibleView() {
            visibility = View.VISIBLE
        }

        fun View.invisibleView() {
            visibility = View.INVISIBLE
        }

        fun Context.showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        fun Activity.transparentStatusBar() {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

            val decorView: View = window.decorView
            val wic = WindowInsetsControllerCompat(window, decorView)
            // change status bar fonts color (true is dark)
            wic.isAppearanceLightStatusBars = false
        }

        @Suppress("DEPRECATION")
        fun Activity.hideNavigationBar() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
                window.insetsController?.hide(WindowInsets.Type.navigationBars())
                window.insetsController?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
            val intent = Intent(this, it)
            intent.putExtras(Bundle().apply(extras))
            startActivity(intent)
        }

        inline fun <reified T> Context.openActivityWithBlock(block: Intent.() -> Unit) {
            val intent = Intent(this, T::class.java)
            intent.block()
            startActivity(intent)
        }

        fun <T> Context.openActivityAndClearApp(it: Class<T>, extras: Bundle.() -> Unit = {}) {
            val intent = Intent(this, it)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtras(Bundle().apply(extras))
            startActivity(intent)
        }

        fun gotoStore(context: Context) {
            try {
                context.startActivity(rateIntentForUrl(context, "market://details"))
            } catch (e: ActivityNotFoundException) {
                context.startActivity(
                    rateIntentForUrl(
                        context,
                        "https://play.google.com/store/apps/details"
                    )
                )
            }
        }

        private fun rateIntentForUrl(context: Context, url: String?): Intent {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(String.format("%s?id=%s", url, context.packageName))
            )
            var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            flags = flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
            intent.addFlags(flags)
            return intent
        }
    }
}