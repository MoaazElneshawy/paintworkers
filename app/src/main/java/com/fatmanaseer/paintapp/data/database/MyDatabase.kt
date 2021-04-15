package com.fatmanaseer.paintapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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

/**
 * Created by Moaaz Elneshawy
 * on 24,March,2021
 */

@Database(
    entities = [Admin::class, Customer::class, Worker::class, Request::class,
        Owner::class, Wallpaper::class, Decorative::class, Order::class, Review::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun adminDao(): AdminDao
    abstract fun ownerDao(): OwnerDao
    abstract fun customerDao(): CustomerDao
    abstract fun workerDao(): WorkerDao
    abstract fun wallpapersDao(): WallpapersDao
    abstract fun decorativeDao(): DecorativeDao
    abstract fun orderDao(): OrdersDao
    abstract fun reviewsDao(): ReviewsDao
    abstract fun requestsDao(): RequestsDao

    companion object {
        private val LOCK = Any()
        private var sInstance: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, "test7sasdaddfs621"
                    )
                        .build()
                }
            }
            return sInstance
        }
    }
}