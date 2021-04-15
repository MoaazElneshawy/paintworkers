package com.fatmanaseer.paintapp.models.wallpapers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


@Entity(tableName = "wallpapers")
class Wallpaper(
    @ColumnInfo(name = "specifications")
    var specifications: String,
    @ColumnInfo(name = "quantities")
    var quantities: Int,
    @ColumnInfo(name = "prices")
    var prices: Double,
    @ColumnInfo(name = "ownerName")
    var ownerName: String,
    @ColumnInfo(name = "image")
    var image: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Int = System.currentTimeMillis().toInt()
)
