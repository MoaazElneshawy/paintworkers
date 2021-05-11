package com.fatmanaseer.decoration.presentation.admin.ui.workers

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.models.worker.Worker

/**
Created by Moaaz Elneshawy
on 25,March,2021
 **/


class WorkersAdapter(
    private val workers: List<Worker>,
    private val listener: OnWorkersActionsListener
) : RecyclerView.Adapter<WorkersAdapter.WorkerViewHolder>() {

    inner class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTV = itemView.findViewById<AppCompatTextView>(R.id.itemUsernameTV)
        val deleteIV = itemView.findViewById<AppCompatImageView>(R.id.itemDeleteUserIV)
        val activeIV = itemView.findViewById<AppCompatImageView>(R.id.itemActiveUserIV)
        val statusTV = itemView.findViewById<AppCompatTextView>(R.id.itemStatusTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_owner_for_admin, parent, false)
        return WorkerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val worker = workers[position]
        holder.userNameTV.text = worker.username
        if (worker.active) {
            holder.statusTV.apply {
                text = "active"
                setTextColor(ColorStateList.valueOf(holder.itemView.context.getColor(android.R.color.holo_green_dark)))
            }
        } else {
            holder.statusTV.apply {
                text = "not active"
                setTextColor(ColorStateList.valueOf(holder.itemView.context.getColor(android.R.color.holo_red_dark)))
            }
        }
        holder.activeIV.setOnClickListener {
            listener.onActiveOwner(worker)
            notifyDataSetChanged()
        }
        holder.deleteIV.setOnClickListener {
            listener.onDeleteOwner(worker)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return workers.size
    }
}

interface OnWorkersActionsListener {
    fun onDeleteOwner(worker: Worker)
    fun onActiveOwner(worker: Worker)
}