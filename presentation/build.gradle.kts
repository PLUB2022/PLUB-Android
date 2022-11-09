
plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK

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

    implementation(Libraries.COIL)
    implementation(Libraries.TIMBER)
    implementation(Libraries.LOTTIE)
    implementation(Libraries.INDICATOR)

    implementation(Google.MATERIAL)
    implementation(Google.HILT_ANDROID)

    implementation(Glide.GLIDE)
    annotationProcessor(Glide.GLIDE_COMPILER)

    kapt(Google.HILT_ANDROID_COMPILER)
}