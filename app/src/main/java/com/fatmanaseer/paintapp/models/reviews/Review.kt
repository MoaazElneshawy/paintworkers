package com.fatmanaseer.paintapp.models.reviews

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Moaaz Elneshawy
on 26,March,2021
 **/

@Entity(tableName = "reviews")
class Review(
    @ColumnInfo(name = "review") var reviews: String,
    @ColumnInfo(name = "worker_username") var workerUsername: String,
    @ColumnInfo(name = "customer_username") var customerUsername: String,
    @ColumnInfo(name = "rate") var rate: Float,
    @PrimaryKey(autoGenerate = true)
    var id: Int = System.currentTimeMillis().toInt()
)