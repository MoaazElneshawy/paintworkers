package com.fatmanaseer.paintapp.presentation.customer.ui.requests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.requests.Request
import kotlinx.coroutines.runBlocking

class RequestsViewModel(context: Application) : AndroidViewModel(context) {

    private val repo = DatabaseRepo(context)

    fun getRequest(customerName: String): LiveData<List<Request>>? {
        return repo.getAllRequestsForCustomer(customerName)
    }

    fun acceptRequest(id: Int) {
        runBlocking { repo.acceptRequest(id) }
    }


}