package com.fatmanaseer.paintapp.models.requests

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
Created by Moaaz Elneshawy
on 26,March,2021
 **/

@Dao
interface RequestsDao {

    @Query("SELECT * FROM REQUESTS where ownerName=:ownerName")
    fun getAllRequestsForOwner(ownerName: String): LiveData<List<Request>>

    @Query("SELECT * FROM REQUESTS where customer_username=:customerName")
    fun getAllRequestsForCustomer(customerName: String): LiveData<List<Request>>

    @Insert
    fun insertRequest(request: Request)

    @Query("UPDATE REQUESTS SET accpeted=:accept where id=:id")
    fun acceptRequest(accept: Boolean, id: Int)
}