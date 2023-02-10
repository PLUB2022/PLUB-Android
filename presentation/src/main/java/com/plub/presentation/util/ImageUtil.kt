package com.plub.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.plub.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageUtil @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resourceProvider: ResourceProvider
) {

    companion object {
        const val PREFIX = ".jpg"

        private const val MAX_IMAGE_WIDTH = 1280
        private const val MAX_IMAGE_HEIGHT = 1280
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

    /**
     * ImageCrop 라이브러리의 ContractOptions를 가져오는 함수
     */
    fun getCropImageOptions(defaultUri: Uri): CropImageContractOptions {
        return CropImageContractOptions(
            uri = defaultUri,
            cropImageOptions = CropImageOptions(
                activityBackgroundColor = ContextCompat.getColor(context, R.color.color_f5f3f6),
                allowFlipping = false
            )
        )
    }

    /**
     * 이미지 최대 사이즈 : 1280 * 1280
     * 이미지 최대 용량 : 6.25MB
     * 로 이미지 최적화한다.
     */
    fun uriToOptimizeImageFile(uri: Uri): File? {
        try {
            val storage = context.cacheDir
            val fileName = String.format("%s.%s", UUID.randomUUID(), "jpg")

            val tempFile = File(storage, fileName)
            tempFile.createNewFile()

            val fos = FileOutputStream(tempFile)

            decodeOptimizeBitmapFromUri(uri)?.apply {
                compress(Bitmap.CompressFormat.JPEG, 100, fos)
                recycle()
            } ?: throw NullPointerException()

            fos.flush()
            fos.close()

            return tempFile

        } catch (e:Exception) {
            PlubLogger.logD("ImageUtil", "${e.message}")
        }

        return null
    }

    /**
     * BitmapFactory를 사용하게 되면 Bitmap.Config는 기본으로 ARGB_8888으로 설정된다.
     * 이미지의 최대 가로/세로 길이가 1280이라고 할 때,
     * Bitmap의 최대 크기는 1280 * 1280 * 4 = 6.25MB가 된다.
     * 즉 해당 함수는 Bitmap을 6.25MB 이하로 만들어준다.
     */
    private fun decodeOptimizeBitmapFromUri(uri: Uri): Bitmap? {

        val input = BufferedInputStream(context.contentResolver.openInputStream(uri))

        input.mark(input.available())

        var bitmap: Bitmap?

        BitmapFactory.Options().run {
            inJustDecodeBounds = true
            bitmap = BitmapFactory.decodeStream(input, null, this)

            input.reset()

            inSampleSize = calculateInSampleSize(this)
            inJustDecodeBounds = false

            bitmap = BitmapFactory.decodeStream(input, null, this)?.apply {
                rotateImageIfRequired(context, this, uri)
            }
        }

        input.close()

        return bitmap

    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > MAX_IMAGE_HEIGHT || width > MAX_IMAGE_WIDTH) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= MAX_IMAGE_HEIGHT && halfWidth / inSampleSize >= MAX_IMAGE_WIDTH) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap? {
        val input = context.contentResolver.openInputStream(uri) ?: return null

        val exif = if (Build.VERSION.SDK_INT > 23) {
            ExifInterface(input)
        } else {
            ExifInterface(uri.path!!)
        }

        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getUriFromTempFileInExternalDir():Uri {
        val fileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val fileName = System.currentTimeMillis().toString()
        val file = File.createTempFile(fileName,PREFIX,fileDir)
        val authority = context.packageName + ".provider"
        return FileProvider.getUriForFile(context, authority, file)
    }
}