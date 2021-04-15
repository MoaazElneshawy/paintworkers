package com.fatmanaseer.paintapp.models.owner

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/

@Dao
interface OwnerDao {
    @Query("Select * from owners")
    fun getAllOwners(): LiveData<List<Owner>>

    @Insert
    fun insertOwner(owner: Owner)
}