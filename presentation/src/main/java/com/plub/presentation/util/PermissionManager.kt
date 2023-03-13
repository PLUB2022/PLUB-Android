package com.plub.presentation.util

import android.Manifest
import android.os.Build
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import javax.inject.Inject
import javax.inject.Singleton

object PermissionManager {

    private fun setPermissionListener(doWhenPermissionGranted: () -> Unit): PermissionListener {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                doWhenPermissionGranted()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                PlubLogger.logD(deniedPermissions.toString())
            }
        }
        return permissionListener
    }

    fun createGetImagePermission(doWhenPermissionGranted: () -> Unit) {
        val permissionListener = setPermissionListener { doWhenPermissionGranted() }

        val permissions = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            }

            else -> {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
            }
        }

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setPermissions(
                *permissions
            )
            .check()
    }
}