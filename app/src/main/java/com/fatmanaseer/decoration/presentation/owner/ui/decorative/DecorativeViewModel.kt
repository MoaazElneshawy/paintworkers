package com.fatmanaseer.decoration.presentation.owner.ui.decorative

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.decorative.Decorative
import kotlinx.coroutines.runBlocking

class DecorativeViewModel(application: Application) : AndroidViewModel(application) {


    private val repo = DatabaseRepo(application)

    fun getDecoration(ownerName: String): LiveData<List<Decorative>>? {
        return repo.getOwnerDecorative(ownerName)
    }

    fun insertDecorative(decorative: Decorative) {
        runBlocking { repo.insertDecorative(decorative) }
    }

    fun deleteDecorative(decorative: Decorative) {
        runBlocking { repo.deleteDecorative(decorative) }
    }
}