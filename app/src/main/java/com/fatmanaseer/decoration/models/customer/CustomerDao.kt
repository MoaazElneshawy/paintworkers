package com.fatmanaseer.decoration.models.customer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatmanaseer.decoration.models.decorative.Decorative
import com.fatmanaseer.decoration.models.wallpapers.Wallpaper
import com.fatmanaseer.decoration.models.worker.Worker

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/

@Dao
interface CustomerDao {
    @Query("Select * from customers")
    fun getAllCustomers(): LiveData<List<Customer>>

    @Insert
    fun insertCustomer(customer: Customer)


    @Query("SELECT * FROM decorations")
    fun getAllDecorative(): LiveData<List<Decorative>>

    @Query("SELECT * FROM wallpapers")
    fun getAllWallpapers(): LiveData<List<Wallpaper>>

    @Query("SELECT * FROM workers")
    fun getAllWorkers(): LiveData<List<Worker>>

}