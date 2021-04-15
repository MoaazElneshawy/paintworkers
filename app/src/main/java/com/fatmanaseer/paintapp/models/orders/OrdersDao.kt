package com.fatmanaseer.paintapp.models.orders

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
Created by Moaaz Elneshawy
on 26,March,2021
 **/

@Dao
interface OrdersDao {

    @Query("SELECT * FROM orders where worker_username=:workerUsername")
    fun getAllOrdersForWorker(workerUsername: String): LiveData<List<Order>>

    @Query("UPDATE orders SET cost=:cost where id=:id")
    fun updateCostForOrder(id: Int, cost: String)

    @Insert
    fun insertOrder(order: Order)

    @Query("SELECT * FROM orders where customer_username=:customerUsername")
    fun getAllOrdersForCustomer(customerUsername: String): LiveData<List<Order>>

}