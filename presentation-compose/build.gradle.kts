plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.plub.presentation_compose"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(project(":domain"))
    implementation(project(":design-system"))

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

    implementation(Libraries.CALENDAR_VIEW)
    implementation(Libraries.COIL)
    implementation(Libraries.TIMBER)
    implementation(Libraries.LOTTIE)
    implementation(Libraries.INDICATOR)
    implementation(Libraries.KAKAO)
    implementation(Libraries.TED_PERMISSION)
    implementation(Libraries.THREE_TEN_ABP)
    implementation(Libraries.IMAGE_CROPPER)
    implementation(Libraries.POWER_MENU)

    implementation(Google.MATERIAL)
    implementation(Google.HILT_ANDROID)
    implementation(Google.GOOGLE_PLAY_SERVICE)
    implementation(Google.FLEX_BOX)
    implementation(Google.FIREBASE_BOM)
    implementation(Google.FCM)
    implementation(Google.FCM_KTX)

    implementation(Glide.GLIDE)
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
    annotationProcessor(Glide.GLIDE_COMPILER)

    implementation(Compose.COMPOSE_ACTIVITY)
    implementation(Compose.COMPOSE_MATERIAL)
    implementation(Compose.COMPOSE_PREVIEW)
    implementation(Compose.COMPOSE_UI)
    implementation(Compose.COMPOSE_NAV)
    implementation(Compose.COMPOSE_ANI_NAV)
    implementation(Compose.COMPOSE_UI_TOOL)

    kapt(Google.HILT_ANDROID_COMPILER)
}