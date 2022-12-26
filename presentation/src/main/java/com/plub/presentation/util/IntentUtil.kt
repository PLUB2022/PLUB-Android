package com.plub.presentation.util

import android.content.Intent
import android.provider.MediaStore

object IntentUtil {
    fun getSingleImageIntent(): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )

        return intent
    }
}