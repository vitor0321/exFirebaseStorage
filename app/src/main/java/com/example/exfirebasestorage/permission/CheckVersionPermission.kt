package com.example.exfirebasestorage.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.exfirebasestorage.MainActivity
import com.example.exfirebasestorage.constants.REQUEST_GALLERY
import com.example.exfirebasestorage.constants.REQUEST_IMAGE_CAPTURE
import com.example.exfirebasestorage.exhibition.Exhibition.dispatchTakeExhibition
import com.example.exfirebasestorage.exhibition.Exhibition.galleryExhibition


object CheckVersionPermission {
    fun galleryPermission(activity: MainActivity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            REQUEST_GALLERY
                        )
                    }
                    else -> {
                        galleryExhibition(activity)
                    }
                }
            } else Toast.makeText(activity, "Version less 23", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun dispatchTakePermission(
        activity: MainActivity
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    PermissionChecker.checkSelfPermission(
                        activity,
                        Manifest.permission.CAMERA
                    ) == PermissionChecker.PERMISSION_GRANTED -> {
                        ActivityCompat.requestPermissions(
                            Activity(),
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_IMAGE_CAPTURE
                        )
                    }
                    else -> dispatchTakeExhibition(activity)
                }
            } else
                Toast.makeText(activity, "Version less 23", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }
    }
}