package com.fatmanaseer.decoration.presentation.admin.ui.workers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.decoration.data.database.DatabaseRepo
import com.fatmanaseer.decoration.models.worker.Worker
import kotlinx.coroutines.runBlocking

class WorkersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DatabaseRepo(application)

    val workers: LiveData<List<Worker>>?
        get() = repo.getAllWorkersForAdmin()

    fun activeWorker(worker: Worker) {
        runBlocking { repo.activeWorkerByAdmin(worker) }
    }

    fun deleteWorker(workerUsername: String) {
        runBlocking { repo.deleteWorkerByAdmin(workerUsername) }
    }
}
