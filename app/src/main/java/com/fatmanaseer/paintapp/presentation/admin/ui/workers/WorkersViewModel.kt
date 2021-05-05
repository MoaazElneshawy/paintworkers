package com.fatmanaseer.paintapp.presentation.admin.ui.workers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.data.database.DatabaseRepo
import com.fatmanaseer.paintapp.models.worker.Worker
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
