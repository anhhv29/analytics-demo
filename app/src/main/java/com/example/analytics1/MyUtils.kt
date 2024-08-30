package com.example.analytics1

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast

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

        fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
            val intent = Intent(this, it)
            intent.putExtras(Bundle().apply(extras))
            startActivity(intent)
        }

        fun <T> Context.openActivityAndClearApp(it: Class<T>, extras: Bundle.() -> Unit = {}) {
            val intent = Intent(this, it)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtras(Bundle().apply(extras))
            startActivity(intent)
        }

        fun Context.gotoStore() {
            try {
                this.startActivity(rateIntentForUrl(this, "market://details"))
            } catch (e: ActivityNotFoundException) {
                this.startActivity(
                    rateIntentForUrl(
                        this,
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