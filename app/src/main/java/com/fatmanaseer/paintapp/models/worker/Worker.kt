package com.fatmanaseer.paintapp.models.worker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatmanaseer.paintapp.models.admin.Admin

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/

@Entity(tableName = "workers")
class Worker(
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "active")
    var active: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = System.currentTimeMillis().toInt()
) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Worker)
            return false
        return username == other.username && password == other.password
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + id
        return result
    }

}