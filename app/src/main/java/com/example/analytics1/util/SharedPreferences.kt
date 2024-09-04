package com.example.analytics1.util

import android.content.Context
import android.content.SharedPreferences
import com.example.analytics1.util.Constants.Language.Companion.ENGLISH

class SharedPreferences {
    companion object {
        private const val APP_SETTINGS = "APP_SETTINGS"
        private const val LANGUAGE_APP = "LANGUAGE_APP"

        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
        }

        fun getLanguage(context: Context): String? {
            return getSharedPreferences(context).getString(LANGUAGE_APP, ENGLISH)
        }

        fun setLanguage(context: Context, value: String) {
            val editor = getSharedPreferences(context).edit()
            editor.putString(LANGUAGE_APP, value)
            editor.apply()
        }
    }
}