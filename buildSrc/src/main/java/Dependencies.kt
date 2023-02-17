object Kotlin {
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINE}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINE}"
    const val KOTLIN_SERIALIZATION_JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION_JSON}"
}


object AndroidX {
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_KTX}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.VIEW_MODEL_KTX}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:${Versions.LEGACY}"
    const val DATA_STORE_PROTO = "androidx.datastore:datastore:${Versions.DATA_STORE}"
    const val DATA_STORE_PREFERENCES = "androidx.datastore:datastore-preferences:${Versions.DATA_STORE}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLER_VIEW}"
    const val PAGING3_WITHOUT_ANDROID_DEPENDENCY = "androidx.paging:paging-common:${Versions.PAGING3}"
    const val PAGING3_RUNTIME = "androidx.paging:paging-runtime:${Versions.PAGING3}"
    const val PAGING3_COMMON_KTX = "androidx.paging:paging-common-ktx:${Versions.PAGING3}"
    const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
}

object Google {
    const val GOOGLE_PLAY_SERVICE = "com.google.android.gms:play-services-auth:${Versions.GOOGLE_PLAY_SERVICE}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val HILT_CORE = "com.google.dagger:hilt-core:${Versions.HILT}"

    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

    const val TINK = "com.google.crypto.tink:tink-android:${Versions.TINK}"
    const val PROTOBUF = "com.google.protobuf:protobuf-javalite:${Versions.PROTOBUF}"
    const val PROTOC = "com.google.protobuf:protoc:${Versions.PROTOBUF}"
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
    const val FLEX_BOX = "com.google.android.flexbox:flexbox:${Versions.FLEX_BOX}"
}

object Libraries {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    const val COIL = "io.coil-kt:coil-compose:${Versions.COIL}"

    const val LOTTIE = "com.airbnb.android:lottie:${Versions.LOTTIE}"
    const val INDICATOR = "com.tbuonomo:dotsindicator:${Versions.INDICATOR}"

    const val KAKAO = "com.kakao.sdk:v2-user:${Versions.KAKAO}"

    const val TED_PERMISSION = "io.github.ParkSangGwon:tedpermission-normal:${Versions.TED_PERMISSION}"

    /**
     * API 26 미만에서 java.time 패키지를 사용할 수 있게 해주는 라이브러리
     */
    const val THREE_TEN_ABP = "com.jakewharton.threetenabp:threetenabp:${Versions.THREE_TEN_ABP}"

    const val IMAGE_CROPPER = "com.vanniktech:android-image-cropper:${Versions.IMAGE_CROPPER}"
}

object AndroidTest {
    const val ANDROID_JUNIT = "junit:junit:${Versions.ANDROID_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val ANDROID_JUNIT_EXT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT_EXT}"
}

object Glide {
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
}