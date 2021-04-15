package com.fatmanaseer.paintapp.models.customer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatmanaseer.paintapp.models.worker.Worker

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/

@Dao
interface WorkerDao {
    @Query("Select * from workers")
    fun getAllWorkers(): LiveData<List<Worker>>

    @Insert
    fun insertWorker(owner: Worker)
}