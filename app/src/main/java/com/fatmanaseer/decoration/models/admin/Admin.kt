package com.fatmanaseer.decoration.models.admin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Moaaz Elneshawy
on 24,March,2021
 **/

@Entity(tableName = "admins")
class Admin(
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "password")
    var password: String,
    @PrimaryKey(autoGenerate = true) var id: Int = System.currentTimeMillis().toInt()
) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Admin)
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
