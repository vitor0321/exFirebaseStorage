package com.example.exfirebasestorage

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.DATA
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exfirebasestorage.constants.ADD_PHOTO_DIALOG
import com.example.exfirebasestorage.constants.REQUEST_GALLERY
import com.example.exfirebasestorage.constants.REQUEST_IMAGE_CAPTURE
import com.example.exfirebasestorage.databinding.ActivityMainBinding
import com.example.exfirebasestorage.dialog.AddPhotoDialog
import com.example.exfirebasestorage.firebase.FirebaseStorage.deleteImage
import com.example.exfirebasestorage.firebase.FirebaseStorage.downloadImage
import com.example.exfirebasestorage.firebase.FirebaseStorage.listFile
import com.example.exfirebasestorage.firebase.FirebaseStorage.uploadImageToStorage
import com.example.exfirebasestorage.permission.CheckVersionPermission.dispatchTakePermission
import com.example.exfirebasestorage.permission.CheckVersionPermission.galleryPermission


class MainActivity : AppCompatActivity() {

    private var curFile: Uri? = null

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            ivImage.setOnClickListener {
                /*open AddPhotoDialog*/
                AddPhotoDialog.newInstance()
                    .show(supportFragmentManager, ADD_PHOTO_DIALOG)
            }

            btnUploadImage.setOnClickListener {
                uploadImageToStorage("myImage", curFile, this@MainActivity)
            }

            btnDownloadImage.setOnClickListener {
                downloadImage("myImage", binding, this@MainActivity)
            }

            btnDeleteImage.setOnClickListener {
                deleteImage("myImage", this@MainActivity)
            }
        }

        listFile(binding, this@MainActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            when {
                requestCode == REQUEST_GALLERY && resultCode == RESULT_OK -> {
                    data?.data?.let {
                        curFile = it
                        binding.ivImage.setImageURI(it)
                    }
                }
                requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK -> {
                    val imageBitmap = data?.extras?.get(DATA) as Bitmap
                    binding.ivImage.setImageBitmap(imageBitmap)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }
    }
}