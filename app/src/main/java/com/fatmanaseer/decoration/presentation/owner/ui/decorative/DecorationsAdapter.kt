package com.fatmanaseer.decoration.presentation.owner.ui.decorative

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


class DecorationsAdapter(
    private val decoration: List<Decorative>,
    private val listener: OnDeleteDecorativeListener
) :
    RecyclerView.Adapter<DecorationsAdapter.DecorationsViewHolder>() {

    inner class DecorationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<AppCompatImageView>(R.id.itemWallpaperIV)
        val spec = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperSpecTV)
        val quantity = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperQuantityTV)
        val price = itemView.findViewById<AppCompatTextView>(R.id.itemWallpaperPriceTV)
        val delete = itemView.findViewById<AppCompatImageView>(R.id.itemWallpaperDeleteIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecorationsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return DecorationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DecorationsViewHolder, position: Int) {
        val decoration = decoration[position]
        if (decoration.image.isNotEmpty()) {
            val decodedString: ByteArray = Base64.decode(decoration.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            holder.image.setImageBitmap(decodedByte)
        }
        holder.price.text = "Price : ${decoration.prices}"
        holder.quantity.text = "Quantity : ${decoration.quantities}"
        holder.spec.text = decoration.specifications
        holder.delete.setOnClickListener {
            listener.onDelete(decoration)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return decoration.size
    }
}

interface OnDeleteDecorativeListener {

    fun onDelete(decoration: Decorative)

}