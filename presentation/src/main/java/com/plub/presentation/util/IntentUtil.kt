package com.plub.presentation.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.plub.presentation.ui.main.MainActivity
import com.plub.presentation.ui.sign.SignActivity

object IntentUtil {
    fun getSingleImageIntent(): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )

        return intent
    }

    fun getOpenCameraIntent(uri: Uri):Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT,uri)
        }
    }

    fun getMainActivityIntent(context:Context): Intent {
        return Intent(context, MainActivity::class.java)
    }

    fun getSignActivityIntent(context:Context): Intent {
        return Intent(context, SignActivity::class.java)
    }

    fun getFcmPendingIntent(context: Context): PendingIntent {
        val intent = getMainActivityIntent(context)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        return PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}