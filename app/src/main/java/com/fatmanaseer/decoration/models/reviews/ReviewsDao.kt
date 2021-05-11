package com.fatmanaseer.decoration.models.reviews

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
Created by Moaaz Elneshawy
on 26,March,2021
 **/


@Dao
interface ReviewsDao {

    @Query("SELECT * FROM REVIEWS where worker_username=:workerUsername")
    fun getWorkerReviews(workerUsername: String): LiveData<List<Review>>

    @Insert
    fun insertReview(review: Review)

}