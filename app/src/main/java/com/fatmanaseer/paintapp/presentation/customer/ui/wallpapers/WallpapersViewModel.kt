package com.fatmanaseer.paintapp.presentation.customer.ui.wallpapers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.requests.Request
import com.fatmanaseer.paintapp.models.wallpapers.Wallpaper
import com.fatmanaseer.paintapp.models.worker.Worker
import kotlinx.coroutines.runBlocking

class WallpapersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val wallpapers: LiveData<List<Wallpaper>>?
        get() = repo.getAllWallpapersForCustomer()

    fun requestWallpaper(request:Request){
     runBlocking {     repo.insertRequest(request)}
    }
}