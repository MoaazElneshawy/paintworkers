package com.fatmanaseer.paintapp.presentation.customer.ui.workers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.models.worker.Worker

/**
Created by Moaaz Elneshawy
on 26,March,2021
 **/


class WorkersAdapter(
    private val workers: List<Worker>,
    private val listener: OnWorkerClickListener
) : RecyclerView.Adapter<WorkersAdapter.WorkersViewHolder>() {


    inner class WorkersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workerNameTV: AppCompatTextView = itemView.findViewById(R.id.itemWorkerNameTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_worker_fro_customer, parent, false)
        return WorkersViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkersViewHolder, position: Int) {
        val worker = workers[position]
        holder.workerNameTV.text = worker.username
        holder.itemView.setOnClickListener { listener.onWorkerClick(worker) }
    }

    override fun getItemCount() = workers.size
}

interface OnWorkerClickListener {
    fun onWorkerClick(worker: Worker)
}