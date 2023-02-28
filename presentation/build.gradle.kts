
plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Kotlin.KOTLIN_STDLIB)
    implementation(Kotlin.COROUTINES_ANDROID)
    implementation(Kotlin.COROUTINES_CORE)

    implementation(AndroidX.APP_COMPAT)
    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    implementation(AndroidX.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(AndroidX.ACTIVITY_KTX)
    implementation(AndroidX.FRAGMENT_KTX)
    implementation(AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(AndroidX.NAVIGATION_UI_KTX)
    implementation(AndroidX.LEGACY_SUPPORT)
    implementation(AndroidX.RECYCLER_VIEW)
    implementation(AndroidX.PAGING3_RUNTIME)
    implementation(AndroidX.PAGING3_COMMON_KTX)

    implementation(Libraries.COIL)
    implementation(Libraries.TIMBER)
    implementation(Libraries.LOTTIE)
    implementation(Libraries.INDICATOR)
    implementation(Libraries.KAKAO)
    implementation(Libraries.TED_PERMISSION)
    implementation(Libraries.THREE_TEN_ABP)
    implementation(Libraries.IMAGE_CROPPER)

    implementation(Google.MATERIAL)
    implementation(Google.HILT_ANDROID)
    implementation(Google.GOOGLE_PLAY_SERVICE)
    implementation(Google.FLEX_BOX)
    implementation(Google.FIREBASE_BOM)
    implementation(Google.FCM)
    implementation(Google.FCM_KTX)

    implementation(Glide.GLIDE)
    annotationProcessor(Glide.GLIDE_COMPILER)

    kapt(Google.HILT_ANDROID_COMPILER)
}