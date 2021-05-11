package com.fatmanaseer.decoration.presentation.customer.ui.decoratives

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.decorative.Decorative
import com.fatmanaseer.decoration.models.requests.Request
import kotlinx.coroutines.runBlocking

class DecorativesViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val decorative: LiveData<List<Decorative>>?
        get() = repo.getAllDecorativesForCustomer()

    fun requestDecorative(request: Request) {
        runBlocking { repo.insertRequest(request) }
    }
}