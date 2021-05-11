package com.fatmanaseer.decoration.presentation.admin.ui.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.orders.Order

class OrdersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val allOrders: LiveData<List<Order>>?
        get() = repo.getAllOrdersForAdmin()

}