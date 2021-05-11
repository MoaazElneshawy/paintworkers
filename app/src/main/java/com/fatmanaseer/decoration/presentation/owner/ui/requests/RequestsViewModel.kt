package com.fatmanaseer.decoration.presentation.owner.ui.requests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.requests.Request
import kotlinx.coroutines.runBlocking

class RequestsViewModel(context: Application) : AndroidViewModel(context) {

    private val repo = DatabaseRepo(context)

    fun getRequest(ownerName: String): LiveData<List<Request>>? {
        return repo.getAllRequestsForOwner(ownerName)
    }

    fun acceptRequest(id: Int) {
        runBlocking { repo.acceptRequest(id) }
    }


}