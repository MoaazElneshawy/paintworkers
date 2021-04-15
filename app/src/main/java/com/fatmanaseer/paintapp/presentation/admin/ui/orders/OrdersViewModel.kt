package com.fatmanaseer.paintapp.presentation.admin.ui.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.orders.Order

class OrdersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val allOrders: LiveData<List<Order>>?
        get() = repo.getAllOrdersForAdmin()

}