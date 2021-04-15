package com.fatmanaseer.paintapp.models.wallpapers

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fatmanaseer.paintapp.models.decorative.Decorative

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


@Dao
interface DecorativeDao {
    @Query("SELECT * FROM decorations where ownerName==:ownerName")
    fun getOwnerDecorative(ownerName: String): LiveData<List<Decorative>>

    @Insert
    fun insertDecorative(decorative: Decorative)

    @Delete
    fun deleteDecorative(decorative: Decorative)

}