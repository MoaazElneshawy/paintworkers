package com.fatmanaseer.paintapp.data.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.models.admin.Admin
import com.fatmanaseer.paintapp.models.customer.Customer
import com.fatmanaseer.paintapp.models.owner.Owner
import com.fatmanaseer.paintapp.models.worker.Worker
import kotlinx.coroutines.runBlocking

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


class DatabaseViewModel(context: Application) : AndroidViewModel(context) {
    private val repo = DatabaseRepo(context)

    // ADMIN
    val allAdmins: LiveData<List<Admin>>?
        get() = repo.getAllAdmins()

    fun addAdmin(admin: Admin) {
        runBlocking { repo.insertAdmin(admin) }
    }

    // CUSTOMER
    val allCustomers: LiveData<List<Customer>>?
        get() = repo.getAllCustomers()

    fun addCustomer(customer: Customer) {
        runBlocking { repo.insertCustomer(customer) }
    }

    // owners
    val allOwners: LiveData<List<Owner>>?
        get() = repo.getAllOwners()

    fun addOwner(owner: Owner) {
        runBlocking { repo.insertOwner(owner) }
    }

    // workers
    val allWorkers: LiveData<List<Worker>>?
        get() = repo.getAllWorkers()

    fun addWorker(worker: Worker) {
        runBlocking { repo.insertWorker(worker) }
    }


}