package com.fatmanaseer.paintapp.presentation.worker.ui.reviews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.reviews.Review

class ReviewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = DatabaseRepo(application)

    fun getReviews(workerName: String): LiveData<List<Review>>? {
        return repo.getWorkerReviews(workerName)
    }
}