package com.plub.plubandroid

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kakao.sdk.common.KakaoSdk
import com.plub.plubandroid.util.KAKAO_NATIVE_KEY
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PlubApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
        AndroidThreeTen.init(this)
    }
}