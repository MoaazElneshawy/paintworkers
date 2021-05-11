package com.fatmanaseer.decoration.presentation.worker.ui.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.data.sharedData.SharedPref

class ReviewsFragment : Fragment() {

    private lateinit var reviewsViewModel: ReviewsViewModel

    private lateinit var sharedPref: SharedPref

    private lateinit var noReviewsTV: AppCompatTextView
    private lateinit var reviewsRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewsViewModel =
            ViewModelProvider(this).get(ReviewsViewModel::class.java)
        sharedPref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_reviews, container, false)
        noReviewsTV = root.findViewById(R.id.noReviewsTV)
        reviewsRV = root.findViewById(R.id.reviewsRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        reviewsViewModel.getReviews(sharedPref.workerUsername)?.observe(viewLifecycleOwner) {
            it?.let { reviews ->
                if (reviews.isEmpty()) {
                    noReviewsTV.visibility = View.VISIBLE
                    reviewsRV.visibility = View.GONE
                } else {
                    noReviewsTV.visibility = View.GONE
                    reviewsRV.apply {
                        visibility = View.VISIBLE
                        layoutManager = LinearLayoutManager(this@ReviewsFragment.requireContext())
                        adapter = ReviewsAdapter(reviews)
                    }
                }
            }
        }
    }
}