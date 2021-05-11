package com.fatmanaseer.decoration.presentation.customer.ui.decoratives

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.models.decorative.Decorative


/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


class DecorativeAdapter(
    private val decoratives: List<Decorative>,
    private val listener: OnRequestDecorativeListener
) :
    RecyclerView.Adapter<DecorativeAdapter.DecorativesViewHolder>() {

    inner class DecorativesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<AppCompatImageView>(R.id.itemWallpaperIV)
        val spec = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperSpecTV)
        val quantity = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperQuantityTV)
        val price = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperPriceTV)
        val request = itemView.findViewById<AppCompatImageView>(R.id.itemWallpaperRequestIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecorativesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper_for_customer, parent, false)
        return DecorativesViewHolder(view)
    }

    override fun onBindViewHolder(holder: DecorativesViewHolder, position: Int) {
        val decorative = decoratives[position]
        if (decorative.image.isNotEmpty()) {
            val decodedString: ByteArray = Base64.decode(decorative.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            holder.image.setImageBitmap(decodedByte)
        }
        holder.price.text = "Price : ${decorative.prices}"
        holder.quantity.text = "Quantity : ${decorative.quantities}"
        holder.spec.text = "Owner : ${decorative.ownerName}\n${decorative.specifications}"
        holder.request.setOnClickListener {
            listener.onRequestDecorative(decorative)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return decoratives.size
    }
}

interface OnRequestDecorativeListener {
    fun onRequestDecorative(decorative: Decorative)
}