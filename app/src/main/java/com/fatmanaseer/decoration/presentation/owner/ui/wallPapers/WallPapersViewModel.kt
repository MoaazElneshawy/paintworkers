package com.fatmanaseer.decoration.presentation.owner.ui.wallPapers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.wallpapers.Wallpaper
import kotlinx.coroutines.runBlocking

class WallPapersViewModel(context: Application) : AndroidViewModel(context) {

    private val repo = DatabaseRepo(context)

    // wallpapers

    fun getWallpapers(ownerName: String): LiveData<List<Wallpaper>>? {
        return repo.getOwnerWallpapers(ownerName)
    }

    fun insertWallpaper(wallpaper: Wallpaper) {
        runBlocking { repo.insertWallpaper(wallpaper) }
    }

    fun deleteWallpaper(wallpaper: Wallpaper) {
        runBlocking { repo.deleteWallPaper(wallpaper) }
    }

}