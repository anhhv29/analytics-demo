import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlyticsGradle)
}

android {
    namespace = "com.example.analytics1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.analytics1"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val formattedDate = SimpleDateFormat("MM.dd.yyyy").format(Date())
        val archivesBaseName =
            "${rootProject.name}_v${versionName}_c${versionCode}_${formattedDate}"
        setProperty("archivesBaseName", archivesBaseName)
    }

    signingConfigs {
        create("release") {
            keyAlias = "cscmobiapps"
            keyPassword = "cscmobi"
            storeFile = file("keystore/cscmobiapps.jks")
            storePassword = "cscmobi"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            multiDexEnabled = true
            isShrinkResources = false
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
        }

        release {
            //debug for build release
            isDebuggable = true

//            isDebuggable = false
            isMinifyEnabled = true
            multiDexEnabled = true
            isShrinkResources = true
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.multidex)
    implementation(libs.lifecycle.process)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)
    implementation(libs.play.services.ads)
    implementation(libs.user.messaging.platform)
    implementation(libs.lottie)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.glide)
    implementation(libs.shimmer)
}