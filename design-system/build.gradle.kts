plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.plub.design_system"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.plub.design_system"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPLIER
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.APP_COMPAT)
    implementation(Google.MATERIAL)

    implementation(Compose.COMPOSE_ACTIVITY)
    implementation(Compose.COMPOSE_MATERIAL)
    implementation(Compose.COMPOSE_PREVIEW)
    implementation(Compose.COMPOSE_UI)
    implementation(Compose.COMPOSE_NAV)
    implementation(Compose.COMPOSE_ANI_NAV)
    implementation(Compose.COMPOSE_UI_TOOL)

    androidTestImplementation(AndroidTest.ANDROID_JUNIT)
    androidTestImplementation(AndroidTest.ESPRESSO_CORE)
    androidTestImplementation(AndroidTest.ANDROID_JUNIT_EXT)
}
