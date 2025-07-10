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
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.analytics1"
        minSdk = 24
        targetSdk = 36
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

        create("staging") {
            keyAlias = "cscmobiapps"
            keyPassword = "cscmobi"
            storeFile = file("keystore/cscmobiapps.jks")
            storePassword = "cscmobi"
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            multiDexEnabled = true
            isShrinkResources = false
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
        }

        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            multiDexEnabled = true
            isShrinkResources = true
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        create("staging") {
            initWith(getByName("debug"))
            isMinifyEnabled = true
            isDebuggable = true
            multiDexEnabled = true
            isShrinkResources = true
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
            signingConfig = signingConfigs.getByName("staging")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    buildFeatures {
        viewBinding = true
    }

    flavorDimensions += "default"
    productFlavors {
        create("dev") {}
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