import com.google.protobuf.gradle.*

plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.protobuf") version "0.8.19"
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
}

dependencies {
    implementation(project(":domain"))

    implementation(Kotlin.KOTLIN_STDLIB)
    implementation(Kotlin.COROUTINES_ANDROID)
    implementation(Kotlin.COROUTINES_CORE)
    implementation(Kotlin.KOTLIN_SERIALIZATION_JSON)

    implementation(AndroidX.DATA_STORE_PROTO)
    implementation(AndroidX.DATA_STORE_PREFERENCES)

    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Libraries.TIMBER)

    implementation(Google.HILT_ANDROID)
    implementation(Google.TINK)
    implementation(Google.PROTOBUF)
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    kapt(Google.HILT_ANDROID_COMPILER)
}

// protobuf plugin
protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = Google.PROTOC
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("java") {
                    option("lite")
                }
            }

        }
    }
}