package com.fatmanaseer.paintapp.presentation.customer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.data.sharedData.SharedPref
import com.fatmanaseer.paintapp.presentation.defaultForAll.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        sharedPref = SharedPref(this)

        Toast.makeText(
            this,
            "welcome ${sharedPref.customerUsername}",
            Toast.LENGTH_SHORT
        ).show()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.customer_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_customer_decoratives,
                R.id.navigation_customer_wallpapers,
                R.id.navigation_customer_workers,
                R.id.navigation_customer_requests,
                R.id.navigation_customer_orders
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                sharedPref.adminUsername = ""
                sharedPref.workerUsername = ""
                sharedPref.ownerUsername = ""
                sharedPref.customerUsername = ""
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}