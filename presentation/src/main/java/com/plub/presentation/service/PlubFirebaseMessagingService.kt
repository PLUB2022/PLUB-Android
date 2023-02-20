package com.plub.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.plub.presentation.ui.main.MainActivity

class PlubFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {

    }

    /** 메시지 수신 메서드(포그라운드) */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Notification 메시지를 수신할 경우
        // remoteMessage.notification?.body!! 여기에 내용이 저장되있음
        // Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)

        //받은 remoteMessage의 값 출력해보기. 데이터메세지 / 알림메세지
    }

    /** 알림 생성 메서드 */
    private fun sendNotification(remoteMessage: RemoteMessage) {

    }
}