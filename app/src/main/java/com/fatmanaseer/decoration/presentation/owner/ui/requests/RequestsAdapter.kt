package com.fatmanaseer.decoration.presentation.owner.ui.requests

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.models.requests.Request


/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


class RequestsAdapter(
    private val requests: List<Request>,
    private val listener: OnAcceptRequestListener
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
            "Type : ${request.type}\nCustomer : ${request.customerUsername}\n${request.specifications}"
        if (request.accepted) {
            holder.accept.visibility = View.GONE
        } else {
            holder.accept.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    listener.onAcceptRequest(request)
                    notifyDataSetChanged()
                }
            }
        }
        holder.acceptTV.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return requests.size
    }
}

interface OnAcceptRequestListener {
    fun onAcceptRequest(request: Request)
}