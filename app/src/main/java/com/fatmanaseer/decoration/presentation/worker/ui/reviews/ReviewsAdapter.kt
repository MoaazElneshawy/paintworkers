package com.fatmanaseer.decoration.presentation.worker.ui.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.models.reviews.Review

/**
Created by Moaaz Elneshawy
on 27,March,2021
 **/


class ReviewsAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    inner class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reviewTV = itemView.findViewById<AppCompatTextView>(R.id.itemReviewNameTV)
        val reviewRate = itemView.findViewById<RatingBar>(R.id.itemReviewRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val review = reviews[position]
        holder.reviewTV.text =
            "From Customer : ${review.customerUsername}\nReview : ${review.reviews}"
        holder.reviewRate.rating = review.rate
    }

    override fun getItemCount() = reviews.size
}