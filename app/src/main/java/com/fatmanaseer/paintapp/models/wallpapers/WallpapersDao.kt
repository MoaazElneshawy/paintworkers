package com.fatmanaseer.paintapp.models.wallpapers

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


@Dao
interface WallpapersDao {
    @Query("SELECT * FROM WALLPAPERS where ownerName==:ownerName")
    fun getOwnerWallpapers(ownerName: String): LiveData<List<Wallpaper>>

    @Insert
    fun insertWallpaper(wallpaper: Wallpaper)

    @Delete
    fun deleteWallpaper(wallpaper: Wallpaper)

}