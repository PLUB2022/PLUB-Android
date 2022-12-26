package com.plub.presentation.util

import android.Manifest
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor() {

    private fun setPermissionListener(doWhenPermissionGranted : () -> Unit): PermissionListener {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                doWhenPermissionGranted()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {}
        }
        return permissionListener
    }

    fun createGetImagePermission(doWhenPermissionGranted : () -> Unit) {
        val permissionListener = setPermissionListener { doWhenPermissionGranted() }

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .check()
    }
}