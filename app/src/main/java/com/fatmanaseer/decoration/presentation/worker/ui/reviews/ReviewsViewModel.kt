package com.fatmanaseer.decoration.presentation.worker.ui.reviews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.reviews.Review

class ReviewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = DatabaseRepo(application)

    fun getReviews(workerName: String): LiveData<List<Review>>? {
        return repo.getWorkerReviews(workerName)
    }
}