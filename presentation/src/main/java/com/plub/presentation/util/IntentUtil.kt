package com.plub.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.plub.presentation.ui.main.MainActivity

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
}