package com.fatmanaseer.paintapp.presentation.customer.ui.workers

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.models.wallpapers.Wallpaper


/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


class WallpapersAdapter(
    private val wallpapers: List<Wallpaper>,
    private val listener: OnRequestWallpaperListener
) :
    RecyclerView.Adapter<WallpapersAdapter.WallpapersViewHolder>() {

    inner class WallpapersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<AppCompatImageView>(R.id.itemWallpaperIV)
        val spec = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperSpecTV)
        val quantity = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperQuantityTV)
        val price = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperPriceTV)
        val request = itemView.findViewById<AppCompatImageView>(R.id.itemWallpaperRequestIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpapersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper_for_customer, parent, false)
        return WallpapersViewHolder(view)
    }

    override fun onBindViewHolder(holder: WallpapersViewHolder, position: Int) {
        val wallpaper = wallpapers[position]
        if (wallpaper.image.isNotEmpty()) {
            val decodedString: ByteArray = Base64.decode(wallpaper.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            holder.image.setImageBitmap(decodedByte)
        }
        holder.price.text = "Price : ${wallpaper.prices}"
        holder.quantity.text = "Quantity : ${wallpaper.quantities}"
        holder.spec.text = "Owner : ${wallpaper.ownerName}\n${wallpaper.specifications}"
        holder.request.setOnClickListener {
            listener.onRequestWallpaper(wallpaper)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return wallpapers.size
    }
}

interface OnRequestWallpaperListener {
    fun onRequestWallpaper(wallpaper: Wallpaper)
}