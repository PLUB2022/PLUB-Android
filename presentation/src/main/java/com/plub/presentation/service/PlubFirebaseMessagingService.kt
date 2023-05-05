package com.plub.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.plub.presentation.R
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.NotificationUtil

class PlubFirebaseMessagingService : FirebaseMessagingService() {

    companion object {

        const val CHANNEL_ID = "notification_remote_channel"
        private const val TITLE = "title"
        private const val BODY = "body"
        private const val TYPE = "type"
        private const val REDIRECT_TARGET_ID = "redirectTargetId"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotificationChannel()
        sendNotification(message.data)
    }

    private fun createNotificationChannel() {
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { NotificationManager.IMPORTANCE_HIGH }
        else { NotificationCompat.PRIORITY_HIGH }

        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID, this.getString(R.string.notification_channel_name), importance)
        } else return

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager.createNotificationChannel(channel)
    }

    private fun sendNotification(data: Map<String, String>) {

        val title = data[TITLE] ?: ""
        val body = data[BODY] ?: ""
        val type = data[TYPE] ?: ""
        val redirectTargetId = data[REDIRECT_TARGET_ID]?.toIntOrNull() ?: 0

        val pendingIntent = IntentUtil.getFcmPendingIntent(this, NotificationUtil.getBundleAndDestination(type, redirectTargetId))

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else NotificationCompat.Builder(this)

        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .setAutoCancel(true)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}