package com.example.exfirebasestorage.firebase

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exfirebasestorage.MainActivity
import com.example.exfirebasestorage.adapter.ImageAdapter
import com.example.exfirebasestorage.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object FirebaseStorage {

    private val imageRef = Firebase.storage.reference

    fun listFile(binding: ActivityMainBinding, mainActivity: MainActivity) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val images = imageRef.child("images/").listAll().await()
                val imagesUrls = mutableListOf<String>()
                for (image in images.items) {
                    val url = image.downloadUrl.await()
                    imagesUrls.add(url.toString())
                }
                withContext(Dispatchers.Main) {
                    val imageAdapter = ImageAdapter(imagesUrls)
                    binding.rvImages.apply {
                        adapter = imageAdapter
                        layoutManager = LinearLayoutManager(context)
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(mainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    fun deleteImage(fileName: String, mainActivity: MainActivity) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                imageRef.child("images/$fileName").delete().await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(mainActivity, "Successfully delete Image", Toast.LENGTH_SHORT)
                        .show()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(mainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    fun downloadImage(fileName: String, binding: ActivityMainBinding, mainActivity: MainActivity) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val maxDownloadSize = 5L * 1024 * 1024
                val bytes = imageRef.child("images/$fileName").getBytes(maxDownloadSize).await()
                val bpm = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                withContext(Dispatchers.Main) {
                    binding.ivImage.setImageBitmap(bpm)
                    Toast.makeText(mainActivity, "Successfully download Image", Toast.LENGTH_SHORT)
                        .show()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(mainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    fun uploadImageToStorage(fileName: String, curFile: Uri?, mainActivity: MainActivity) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                curFile?.let {
                    imageRef.child("images/$fileName").putFile(it).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            mainActivity,
                            "Successfully upload image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(mainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
}