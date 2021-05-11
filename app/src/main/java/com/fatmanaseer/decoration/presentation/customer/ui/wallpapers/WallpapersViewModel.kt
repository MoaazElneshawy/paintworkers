package com.fatmanaseer.decoration.presentation.customer.ui.wallpapers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.requests.Request
import com.fatmanaseer.decoration.models.wallpapers.Wallpaper
import kotlinx.coroutines.runBlocking

class WallpapersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val wallpapers: LiveData<List<Wallpaper>>?
        get() = repo.getAllWallpapersForCustomer()

    fun requestWallpaper(request: Request) {
        runBlocking { repo.insertRequest(request) }
    }
}