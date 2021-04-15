package com.fatmanaseer.paintapp.models.orders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Moaaz Elneshawy
on 26,March,2021
 **/

@Entity(tableName = "orders")
class Order(
    @ColumnInfo(name = "order_details")
    var orderDetails: String,
    @ColumnInfo(name = "cost")
    var cost: String = "0",
    @ColumnInfo(name = "customer_username")
    var customerUsername: String,
    @ColumnInfo(name = "worker_username")
    var workerUsername: String,
    @PrimaryKey @ColumnInfo(name = "id")
    var id: Int = System.currentTimeMillis().toInt(),
    @ColumnInfo(name = "hasReview")
    var hasReview: Boolean = false
)