package com.fatmanaseer.decoration.presentation.customer.ui.workers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.orders.Order
import com.fatmanaseer.decoration.models.requests.Request
import com.fatmanaseer.decoration.models.worker.Worker
import kotlinx.coroutines.runBlocking

class WorkersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val workers: LiveData<List<Worker>>?
        get() = repo.getAllWorkersForCustomer()

    fun getRequest(customerName: String): LiveData<List<Request>>? {
        return repo.getAllRequestsForCustomer(customerName)
    }

    fun makeOrder(order: Order) {
        runBlocking { repo.insertOrder(order) }
    }
}