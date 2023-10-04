import com.google.protobuf.gradle.*

plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.protobuf") version "0.9.1"
    kotlin("android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = Configs.COMPILE_SDK
    namespace = "com.plub.data"

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
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
    implementation(AndroidX.PAGING3_RUNTIME)
    implementation(AndroidX.PAGING3_COMMON_KTX)
    implementation(AndroidX.ROOM)
    implementation(AndroidX.ROOM_KTX)
    ksp(AndroidX.ROOM_COMPILER)

    implementation(Libraries.RETROFIT)
    implementation(Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Libraries.TIMBER)

    implementation(Google.HILT_ANDROID)
    implementation(Google.TINK)
    implementation(Google.PROTOBUF)
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
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}