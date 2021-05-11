package com.fatmanaseer.decoration.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fatmanaseer.decoration.models.admin.Admin
import com.fatmanaseer.decoration.models.admin.AdminDao
import com.fatmanaseer.decoration.models.customer.Customer
import com.fatmanaseer.decoration.models.customer.CustomerDao
import com.fatmanaseer.decoration.models.decorative.Decorative
import com.fatmanaseer.decoration.models.decorative.DecorativeDao
import com.fatmanaseer.decoration.models.orders.Order
import com.fatmanaseer.decoration.models.orders.OrdersDao
import com.fatmanaseer.decoration.models.owner.Owner
import com.fatmanaseer.decoration.models.owner.OwnerDao
import com.fatmanaseer.decoration.models.requests.Request
import com.fatmanaseer.decoration.models.requests.RequestsDao
import com.fatmanaseer.decoration.models.reviews.Review
import com.fatmanaseer.decoration.models.reviews.ReviewsDao
import com.fatmanaseer.decoration.models.wallpapers.Wallpaper
import com.fatmanaseer.decoration.models.wallpapers.WallpapersDao
import com.fatmanaseer.decoration.models.worker.Worker
import com.fatmanaseer.decoration.models.worker.WorkerDao

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
                        MyDatabase::class.java, "DecorationDB"
                    )
                        .build()
                }
            }
            return sInstance
        }
    }
}