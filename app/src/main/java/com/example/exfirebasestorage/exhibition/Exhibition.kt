package com.example.exfirebasestorage.exhibition

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.exfirebasestorage.constants.GALLERY_TYPE
import com.example.exfirebasestorage.MainActivity
import com.example.exfirebasestorage.constants.REQUEST_GALLERY
import com.example.exfirebasestorage.constants.REQUEST_IMAGE_CAPTURE

object Exhibition {

    fun galleryExhibition(activity: MainActivity) {
        try {
            val intentGallery = Intent(Intent.ACTION_PICK)
            intentGallery.type = GALLERY_TYPE
            ActivityCompat.startActivityForResult(
                activity, intentGallery,
                REQUEST_GALLERY,
                null
            )
        }catch (e:Exception){
            Toast.makeText(activity, e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun dispatchTakeExhibition(activity: MainActivity) {
        try {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                val packageManager = activity.packageManager
                takePictureIntent.resolveActivity(packageManager)?.also {
                    ActivityCompat.startActivityForResult(
                        activity,
                        takePictureIntent,
                        REQUEST_IMAGE_CAPTURE,
                        null
                    )
                }
            }
        } catch (e:Exception){
            Toast.makeText(activity, e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }
    }
}