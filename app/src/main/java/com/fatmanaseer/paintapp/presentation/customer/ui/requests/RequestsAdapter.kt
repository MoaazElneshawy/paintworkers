package com.fatmanaseer.paintapp.presentation.customer.ui.requests

import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.models.requests.Request


/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


class RequestsAdapter(
    private val requests: List<Request>
) :
    RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {

    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<AppCompatImageView>(R.id.itemRequestIV)
        val spec = itemView.findViewById<AppCompatTextView>(R.id.itemRequestSpecTV)
        val quantity = itemView.findViewById<AppCompatTextView>(R.id.itemRequestQuantityTV)
        val price = itemView.findViewById<AppCompatTextView>(R.id.itemRequestPriceTV)
        val accept = itemView.findViewById<AppCompatImageView>(R.id.itemAcceptRequestIV)
        val acceptTV = itemView.findViewById<AppCompatTextView>(R.id.itemRequestAcceptedTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_requests_for_owner, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        if (request.image.isNotEmpty()) {
            val decodedString: ByteArray = Base64.decode(request.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            holder.image.setImageBitmap(decodedByte)
        }
        holder.price.text = "Price : ${request.prices}"
        holder.quantity.text = "Quantity : ${request.quantities}"
        holder.spec.text =
            "Type : ${request.type}\nOwner : ${request.ownerName}\n${request.specifications}"
        if (request.accepted) {
            holder.acceptTV.apply {
                visibility = View.VISIBLE
                text = "accepted"
                setTextColor(ColorStateList.valueOf(holder.itemView.context.getColor(android.R.color.holo_green_dark)))
            }
        } else {
            holder.acceptTV.apply {
                visibility = View.VISIBLE
                text = "pending"
                setTextColor(ColorStateList.valueOf(holder.itemView.context.getColor(android.R.color.darker_gray)))
            }
        }
        holder.accept.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return requests.size
    }
}
