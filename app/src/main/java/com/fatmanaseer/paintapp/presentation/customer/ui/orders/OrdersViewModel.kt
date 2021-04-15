package com.fatmanaseer.paintapp.presentation.customer.ui.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.orders.Order
import com.fatmanaseer.paintapp.models.reviews.Review
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

    fun addReview(review: Review){
      runBlocking {   repo.insertReview(review)}
    }
}