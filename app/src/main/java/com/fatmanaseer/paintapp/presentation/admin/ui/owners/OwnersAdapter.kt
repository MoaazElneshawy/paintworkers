package com.fatmanaseer.paintapp.presentation.admin.ui.owners

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.models.owner.Owner

/**
Created by Moaaz Elneshawy
on 25,March,2021
 **/


class OwnersAdapter(
    private val owners: List<Owner>,
    private val listener: OnOwnersActionsListener
) : RecyclerView.Adapter<OwnersAdapter.OwnersViewHolder>() {

    inner class OwnersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTV = itemView.findViewById<AppCompatTextView>(R.id.itemUsernameTV)
        val statusTV = itemView.findViewById<AppCompatTextView>(R.id.itemStatusTV)
        val deleteIV = itemView.findViewById<AppCompatImageView>(R.id.itemDeleteUserIV)
        val activeIV = itemView.findViewById<AppCompatImageView>(R.id.itemActiveUserIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_owner_for_admin, parent, false)
        return OwnersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OwnersViewHolder, position: Int) {
        val owner = owners[position]
        holder.userNameTV.text = owner.username
        if (owner.active) {
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
            listener.onActiveOwner(owner)
            notifyDataSetChanged()
        }
        holder.deleteIV.setOnClickListener {
            listener.onDeleteOwner(owner)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return owners.size
    }
}

interface OnOwnersActionsListener {
    fun onDeleteOwner(owner: Owner)
    fun onActiveOwner(owner: Owner)
}