package com.fatmanaseer.decoration.presentation.customer.ui.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.orders.Order
import com.fatmanaseer.decoration.models.reviews.Review
import kotlinx.coroutines.runBlocking

/**
Created by Moaaz Elneshawy
on 27,March,2021
 **/


class OrdersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    fun getOrders(customerName: String): LiveData<List<Order>>? {
        return repo.getCustomerOrders(customerName)
    }

    fun addReview(review: Review) {
        runBlocking { repo.insertReview(review) }
    }
}