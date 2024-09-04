package com.example.analytics1.util

class Constants {
    class RemoteConfig {
        companion object {
            const val SHOW_BACKGROUND_IMAGE = "show_image"

            //ads open app
            const val KEY_AD_OPEN_APP = "android_ad_open_app"
        }
    }

    class LogEvents {
        companion object {
            const val EVENT_CLICK_TEXT = "android_click_firebase_text"
            const val EVENT_CLICK_IMAGE = "android_click_firebase_image"
            const val EVENT_CLICK_VOICE = "android_click_firebase_voice"
        }
    }

    class Language {
        companion object {
            const val ENGLISH = "EN"
        }
    }
}