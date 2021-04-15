package com.fatmanaseer.paintapp.data.database

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.fatmanaseer.paintapp.models.admin.Admin
import com.fatmanaseer.paintapp.models.admin.AdminDao
import com.fatmanaseer.paintapp.models.customer.Customer
import com.fatmanaseer.paintapp.models.customer.CustomerDao
import com.fatmanaseer.paintapp.models.customer.WorkerDao
import com.fatmanaseer.paintapp.models.decorative.Decorative
import com.fatmanaseer.paintapp.models.orders.Order
import com.fatmanaseer.paintapp.models.orders.OrdersDao
import com.fatmanaseer.paintapp.models.owner.Owner
import com.fatmanaseer.paintapp.models.owner.OwnerDao
import com.fatmanaseer.paintapp.models.requests.Request
import com.fatmanaseer.paintapp.models.requests.RequestsDao
import com.fatmanaseer.paintapp.models.reviews.Review
import com.fatmanaseer.paintapp.models.reviews.ReviewsDao
import com.fatmanaseer.paintapp.models.wallpapers.DecorativeDao
import com.fatmanaseer.paintapp.models.wallpapers.Wallpaper
import com.fatmanaseer.paintapp.models.wallpapers.WallpapersDao
import com.fatmanaseer.paintapp.models.worker.Worker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/


class DatabaseRepo(context: Context) {

    private var adminDao: AdminDao? = null
    private var workerDao: WorkerDao? = null
    private var customerDao: CustomerDao? = null
    private var ownerDao: OwnerDao? = null
    private var wallpapersDao: WallpapersDao? = null
    private var decorativeDao: DecorativeDao? = null
    private var ordersDao: OrdersDao? = null
    private var reviewsDao: ReviewsDao? = null
    private var requestsDao: RequestsDao? = null

    init {
        val databaseInstance = MyDatabase.getInstance(context)
        databaseInstance?.let { db ->
            adminDao = db.adminDao()
            workerDao = db.workerDao()
            customerDao = db.customerDao()
            ownerDao = db.ownerDao()
            wallpapersDao = db.wallpapersDao()
            decorativeDao = db.decorativeDao()
            ordersDao = db.orderDao()
            reviewsDao = db.reviewsDao()
            requestsDao = db.requestsDao()
        }
    }

    //    ADMINS
    fun getAllAdmins(): LiveData<List<Admin>>? {
        return adminDao?.getAllAdmins()
    }

    suspend fun insertAdmin(admin: Admin) {
        CoroutineScope(Dispatchers.IO).async {
            return@async adminDao?.insertAdmin(admin)!!
        }.await()
//        adminDao?.let { InsertAdminAsync(it).execute(admin) }
    }

    private class InsertAdminAsync(val adminDao: AdminDao) : AsyncTask<Admin, Void, Void>() {
        override fun doInBackground(vararg params: Admin?): Void? {
            params[0]?.let { adminDao.insertAdmin(it) }
            return null
        }
    }

    fun getAllOwnersForAdmin(): LiveData<List<Owner>>? {
        return adminDao?.getAllOwners()
    }

    suspend fun activeOwnerByAdmin(owner: Owner) {
        CoroutineScope(Dispatchers.IO).async {
            return@async adminDao?.activeOwnerAccount(!owner.active, owner.username)
        }.await()
    }


    suspend fun deleteOwnerByAdmin(ownerName: String) {
        CoroutineScope(Dispatchers.IO).async {
            return@async adminDao?.deleteOwner(ownerName)
        }.await()
    }

    fun getAllWorkersForAdmin(): LiveData<List<Worker>>? {
        return adminDao?.getAllWorkers()
    }

    suspend fun activeWorkerByAdmin(worker: Worker) {
        CoroutineScope(Dispatchers.IO).async {
            return@async adminDao?.activeWorkerAccount(worker.active.not(), worker.username)
        }.await()
    }

    suspend fun deleteWorkerByAdmin(workerName: String) {
        CoroutineScope(Dispatchers.IO).async {
            return@async adminDao?.deleteWorker(workerName)
        }.await()
    }

    fun getAllOrdersForAdmin(): LiveData<List<Order>>? {
        return adminDao?.getAllOrders()
    }

    // WORKERS
    fun getAllWorkers(): LiveData<List<Worker>>? {
        return workerDao?.getAllWorkers()
    }

    suspend fun insertWorker(worker: Worker) {
        CoroutineScope(Dispatchers.IO).async {
            return@async workerDao?.insertWorker(worker)
        }.await()
    }

    // CUSTOMER
    fun getAllCustomers(): LiveData<List<Customer>>? {
        return customerDao?.getAllCustomers()
    }

    suspend fun insertCustomer(customer: Customer) {
        CoroutineScope(Dispatchers.IO).async {
            return@async customerDao?.insertCustomer(customer)
        }.await()
    }

    fun getAllWorkersForCustomer(): LiveData<List<Worker>>? {
        return customerDao?.getAllWorkers()
    }

    fun getAllWallpapersForCustomer(): LiveData<List<Wallpaper>>? {
        return customerDao?.getAllWallpapers()
    }

    fun getAllDecorativesForCustomer(): LiveData<List<Decorative>>? {
        return customerDao?.getAllDecorative()
    }

    // OWNER
    fun getAllOwners(): LiveData<List<Owner>>? {
        return ownerDao?.getAllOwners()
    }

    suspend fun insertOwner(owner: Owner) {
        CoroutineScope(Dispatchers.IO).async {
            return@async ownerDao?.insertOwner(owner)
        }.await()
    }

    // wallpapers
    fun getOwnerWallpapers(ownerName: String): LiveData<List<Wallpaper>>? {
        return wallpapersDao?.getOwnerWallpapers(ownerName)
    }

    suspend fun insertWallpaper(wallpaper: Wallpaper) {
        CoroutineScope(Dispatchers.IO).async {
            return@async wallpapersDao?.insertWallpaper(wallpaper)
        }.await()
    }

    suspend fun deleteWallPaper(wallpaper: Wallpaper) {
        CoroutineScope(Dispatchers.IO).async {
            return@async wallpapersDao?.deleteWallpaper(wallpaper)
        }.await()
    }

    // decorative
    fun getOwnerDecorative(ownerName: String): LiveData<List<Decorative>>? {
        return decorativeDao?.getOwnerDecorative(ownerName)
    }

    suspend fun insertDecorative(decorative: Decorative) {
        CoroutineScope(Dispatchers.IO).async {
            return@async decorativeDao?.insertDecorative(decorative)
        }.await()
    }

    suspend fun deleteDecorative(decorative: Decorative) {
        CoroutineScope(Dispatchers.IO).async {
            return@async decorativeDao?.deleteDecorative(decorative)
        }.await()
    }

    // orders
    fun getWorkerOrders(workerName: String): LiveData<List<Order>>? {
        return ordersDao?.getAllOrdersForWorker(workerName)
    }

    fun getCustomerOrders(customerName: String): LiveData<List<Order>>? {
        return ordersDao?.getAllOrdersForCustomer(customerName)
    }

    suspend fun updateOrderCost(order: Order) {
        CoroutineScope(Dispatchers.IO).async {
            return@async ordersDao?.updateCostForOrder(order.id, order.cost)
        }.await()
    }

    suspend fun insertOrder(order: Order) {
        CoroutineScope(Dispatchers.IO).async {
            return@async ordersDao?.insertOrder(order)
        }.await()
    }

    // reviews
    fun getWorkerReviews(workerName: String): LiveData<List<Review>>? {
        return reviewsDao?.getWorkerReviews(workerName)
    }

    suspend fun insertReview(review: Review) {
        CoroutineScope(Dispatchers.IO).async {
            return@async reviewsDao?.insertReview(review)
        }.await()
    }

    // request
    fun getAllRequestsForOwner(ownerName: String): LiveData<List<Request>>? {
        return requestsDao?.getAllRequestsForOwner(ownerName)
    }

    fun getAllRequestsForCustomer(customerName: String): LiveData<List<Request>>? {
        return requestsDao?.getAllRequestsForCustomer(customerName)
    }

    suspend fun insertRequest(request: Request) {
        CoroutineScope(Dispatchers.IO).async {
            return@async requestsDao?.insertRequest(request)
        }.await()
    }

    suspend fun acceptRequest(requestId: Int) {
        CoroutineScope(Dispatchers.IO).async {
            return@async requestsDao?.acceptRequest(true, requestId)
        }.await()
    }

}