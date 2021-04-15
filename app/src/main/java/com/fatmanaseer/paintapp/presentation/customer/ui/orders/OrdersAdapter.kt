package com.fatmanaseer.paintapp.presentation.customer.ui.orders

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.models.orders.Order

/**
Created by Moaaz Elneshawy
on 27,March,2021
 **/


class OrdersAdapter(
    private val orders: List<Order>,private val listener: OnReviewOrderListener
) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val detailsTV = itemView.findViewById<AppCompatTextView>(R.id.itemOrderDetailsTV)
        val costET = itemView.findViewById<AppCompatEditText>(R.id.itemOrderCostET)
        val submitCostIV = itemView.findViewById<AppCompatImageView>(R.id.itemSubmitCostIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.detailsTV.text =
            "Details : ${order.orderDetails}\nWorker : ${order.workerUsername}"
        holder.costET.apply {
            isEnabled = false
            setText(order.cost)
        }
        holder.submitCostIV.visibility = View.INVISIBLE
        holder.itemView.setOnClickListener {
            listener.onReviewOrder(order)
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

}

interface OnReviewOrderListener{
    fun onReviewOrder(order: Order)
}