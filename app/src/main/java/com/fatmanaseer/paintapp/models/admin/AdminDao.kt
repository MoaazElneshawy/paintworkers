package com.fatmanaseer.paintapp.models.admin

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatmanaseer.paintapp.models.decorative.Decorative
import com.fatmanaseer.paintapp.models.orders.Order
import com.fatmanaseer.paintapp.models.owner.Owner
import com.fatmanaseer.paintapp.models.wallpapers.Wallpaper
import com.fatmanaseer.paintapp.models.worker.Worker

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/

@Dao
interface
AdminDao {
    @Query("Select * from admins")
    fun getAllAdmins(): LiveData<List<Admin>>

    @Insert
    fun insertAdmin(admin: Admin)

    // owners
    @Query("SELECT * FROM owners")
    fun getAllOwners(): LiveData<List<Owner>>

    @Query("DELETE FROM owners where username=:ownerUsername")
    fun deleteOwner(ownerUsername: String)

    @Query("UPDATE owners SET active =:active where username=:ownerUsername")
    fun activeOwnerAccount(active: Boolean, ownerUsername: String)

    // workers
    @Query("SELECT * FROM workers")
    fun getAllWorkers(): LiveData<List<Worker>>

    @Query("DELETE FROM workers where username=:workerUsername")
    fun deleteWorker(workerUsername: String)

    @Query("UPDATE workers SET active =:active where username=:ownerUsername")
    fun activeWorkerAccount(active: Boolean, ownerUsername: String)

    // orders
    @Query("SELECT * FROM orders")
    fun getAllOrders(): LiveData<List<Order>>

}