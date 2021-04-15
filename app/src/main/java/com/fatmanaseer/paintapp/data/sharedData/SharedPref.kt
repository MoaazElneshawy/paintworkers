package com.fatmanaseer.paintapp.data.sharedData

import android.content.Context
import android.preference.PreferenceManager.getDefaultSharedPreferences

class SharedPref(context: Context) {

    private val OWNER = "currentOwner"
    private val ADMIN = "currentAdmin"
    private val WORKER = "currentWorker"
    private val CUSTOMER = "currentCustomer"

    private val pref by lazy { getDefaultSharedPreferences(context) }

    var ownerUsername: String
        get() = pref.getString(OWNER, "") ?: ""
        set(value) = pref.edit().putString(OWNER, value).apply()

    var adminUsername: String
        get() = pref.getString(ADMIN, "") ?: ""
        set(value) = pref.edit().putString(ADMIN, value).apply()

    var workerUsername: String
        get() = pref.getString(WORKER, "") ?: ""
        set(value) = pref.edit().putString(WORKER, value).apply()

    var customerUsername: String
        get() = pref.getString(CUSTOMER, "") ?: ""
        set(value) = pref.edit().putString(CUSTOMER, value).apply()

}

