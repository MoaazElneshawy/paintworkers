package com.fatmanaseer.paintapp.presentation.admin.ui.owners

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.owner.Owner
import kotlinx.coroutines.runBlocking

class OwnersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val owners: LiveData<List<Owner>>?
        get() = repo.getAllOwnersForAdmin()

    fun activeOwner(owner: Owner) {
        runBlocking { repo.activeOwnerByAdmin(owner) }
    }

    fun deleteOwner(ownerUsername: String) {
        runBlocking {
            repo.deleteOwnerByAdmin(ownerUsername)
        }
    }
}
