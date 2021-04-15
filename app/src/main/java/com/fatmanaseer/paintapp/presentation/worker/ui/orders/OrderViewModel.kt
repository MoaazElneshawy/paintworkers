package com.fatmanaseer.paintapp.presentation.worker.ui.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.orders.Order
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