package com.fatmanaseer.decoration.presentation.defaultForAll

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.data.database.ROLES
import com.fatmanaseer.decoration.data.sharedData.SharedPref

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val sharedPref = SharedPref(this)
        sharedPref.ownerUsername = ""


        val adminBTN: AppCompatButton = findViewById(R.id.adminRoleBTN)
        val ownerBTN: AppCompatButton = findViewById(R.id.ownerRoleBTN)
        val workerBTN: AppCompatButton = findViewById(R.id.workerRoleBTN)
        val customerBTN: AppCompatButton = findViewById(R.id.customerRoleBTN)

        adminBTN.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("ROLE", ROLES.ADMIN)
            startActivity(intent)
        }
        ownerBTN.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("ROLE", ROLES.OWNER)
            startActivity(intent)
        }
        workerBTN.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("ROLE", ROLES.WORKER)
            startActivity(intent)
        }
        customerBTN.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("ROLE", ROLES.CUSTOMER)
            startActivity(intent)
        }
    }
}