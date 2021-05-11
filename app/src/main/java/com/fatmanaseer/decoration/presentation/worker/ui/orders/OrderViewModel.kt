package com.fatmanaseer.decoration.presentation.worker.ui.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.orders.Order
import kotlinx.coroutines.runBlocking

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    fun getMyOrders(workerUsername: String): LiveData<List<Order>>? {
        return repo.getWorkerOrders(workerUsername)
    }

    fun updateCost(order: Order) {
        runBlocking { repo.updateOrderCost(order) }
    }

}