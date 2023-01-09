package com.plub.presentation.util

import android.net.Uri
import android.provider.MediaStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageUtil @Inject constructor(private val resourceProvider: ResourceProvider) {

    companion object {
        const val PREFIX = ".jpg"
    }

    private fun isFailGetColumnIndex(idx: Int) = idx == -1

    fun getRealPathFromURI(uri: Uri): String {
        var columnIndex = -1
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = resourceProvider.getCursor(uri, proj, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        }
        if(isFailGetColumnIndex(columnIndex)) return ""
        val result = cursor!!.getString(columnIndex) // columnIndex에 Path가 존재함
        cursor.close()
        return result
    }
}