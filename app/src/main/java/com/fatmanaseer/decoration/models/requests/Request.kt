package com.fatmanaseer.decoration.models.requests

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Moaaz Elneshawy
on 26,March,2021
 **/

@Entity(tableName = "requests")
class Request(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = System.currentTimeMillis().toInt(),
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "specifications") var specifications: String,
    @ColumnInfo(name = "quantities") var quantities: Int,
    @ColumnInfo(name = "prices") var prices: Double,
    @ColumnInfo(name = "customer_username") var customerUsername: String,
    @ColumnInfo(name = "ownerName") var ownerName: String,
    @ColumnInfo(name = "image") var image: String = "",
    @ColumnInfo(name = "accpeted") var accepted: Boolean = false
)