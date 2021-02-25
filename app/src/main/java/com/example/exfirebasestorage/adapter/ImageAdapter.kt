package com.example.exfirebasestorage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exfirebasestorage.databinding.ItemImageBinding

class ImageAdapter(
    val urls: List<String>
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private var _binding: ItemImageBinding? = null
    private val binding: ItemImageBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        _binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding.root, binding)

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = urls[position]
        val image = binding.ivImage
        Glide.with(holder.itemView)
            .load(url)
            .into(image)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    inner class ImageViewHolder(itemView: View, binding: ItemImageBinding) :
        RecyclerView.ViewHolder(itemView)
}