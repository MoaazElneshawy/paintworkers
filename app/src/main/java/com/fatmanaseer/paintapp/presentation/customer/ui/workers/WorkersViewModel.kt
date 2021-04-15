package com.fatmanaseer.paintapp.presentation.customer.ui.workers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.orders.Order
import com.fatmanaseer.paintapp.models.requests.Request
import com.fatmanaseer.paintapp.models.worker.Worker
import kotlinx.coroutines.runBlocking

class WorkersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val workers: LiveData<List<Worker>>?
        get() = repo.getAllWorkersForCustomer()

    fun getRequest(customerName: String): LiveData<List<Request>>? {
        return repo.getAllRequestsForCustomer(customerName)
    }

    fun makeOrder(order: Order) {
      runBlocking {    repo.insertOrder(order)}
    }
}