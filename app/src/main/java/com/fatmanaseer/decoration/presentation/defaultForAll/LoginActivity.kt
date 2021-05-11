package com.fatmanaseer.decoration.presentation.defaultForAll

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.data.database.DatabaseViewModel
import com.fatmanaseer.decoration.data.database.ROLES
import com.fatmanaseer.decoration.data.sharedData.SharedPref
import com.fatmanaseer.decoration.models.admin.Admin
import com.fatmanaseer.decoration.models.customer.Customer
import com.fatmanaseer.decoration.models.owner.Owner
import com.fatmanaseer.decoration.models.worker.Worker
import com.fatmanaseer.decoration.presentation.admin.AdminActivity
import com.fatmanaseer.decoration.presentation.customer.CustomerActivity
import com.fatmanaseer.decoration.presentation.owner.OwnerActivity
import com.fatmanaseer.decoration.presentation.worker.WorkerActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName

    private lateinit var sharedPref: SharedPref
    private lateinit var dbViewModel: DatabaseViewModel
    private lateinit var role: ROLES

    private lateinit var loginBTN: AppCompatButton
    private lateinit var newAdminBTN: FloatingActionButton
    private lateinit var usernameInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var usernameET: AppCompatEditText
    private lateinit var passwordET: AppCompatEditText
    private lateinit var titleTV: AppCompatTextView

    private lateinit var dialogCreateAccount: AppCompatDialog
    private lateinit var dialogUsernameInput: TextInputLayout
    private lateinit var dialogPasswordInput: TextInputLayout
    private lateinit var dialogUsernameET: AppCompatEditText
    private lateinit var dialogPasswordET: AppCompatEditText

    private val admins = ArrayList<Admin>()
    private val workers = ArrayList<Worker>()
    private val owners = ArrayList<Owner>()
    private val customers = ArrayList<Customer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        sharedPref = SharedPref(this)
        init()
        setOnClicks()
        observeViewModel()
        intent?.extras?.let {
            role = it.getSerializable("ROLE") as ROLES
            titleTV.text = role.name
        }

    }

    private fun init() {
        dbViewModel = ViewModelProvider(this).get(DatabaseViewModel::class.java)
        titleTV = findViewById(R.id.titleTV)
        loginBTN = findViewById(R.id.loginBTN)
        newAdminBTN = findViewById(R.id.createNewBTN)
        usernameET = findViewById(R.id.usernameET)
        usernameInput = findViewById(R.id.usernameInput)
        passwordET = findViewById(R.id.passwordET)
        passwordInput = findViewById(R.id.passwordInput)
    }

    private fun setOnClicks() {
        usernameET.doOnTextChanged { _, _, _, _ -> usernameInput.error = null }
        passwordET.doOnTextChanged { _, _, _, _ -> passwordInput.error = null }
        loginBTN.setOnClickListener { login() }
        newAdminBTN.setOnClickListener {
            showCreateAccountDialog()
        }
    }

    private fun login() {
        val username = usernameET.text.toString().trim()
        if (username.isEmpty()) {
            usernameInput.error = getString(R.string.username_error_empty)
            return
        }
        val password = passwordET.text.toString().trim()
        if (password.isEmpty()) {
            passwordInput.error = getString(R.string.password_error_empty)
            return
        }
        if (password.length < 3) {
            passwordInput.error = getString(R.string.password_error_format)
            return
        }
        when (role) {
            ROLES.ADMIN -> {
                val admin = Admin(username, password)
                if (admins.isNotEmpty()) {
                    if (admins.contains(admin)) {
                        sharedPref.adminUsername = admin.username
                        val intent = Intent(this, AdminActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "${admin.username} is not registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.no_admin_registered),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            ROLES.CUSTOMER -> {
                val customer = Customer(username, password)
                if (customers.isNotEmpty()) {
                    if (customers.contains(customer)) {
                        sharedPref.customerUsername = customer.username
                        val intent = Intent(this, CustomerActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "${customer.username} is not registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.no_customer_registered),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            ROLES.WORKER -> {
                val worker = Worker(username, password)
                if (workers.isNotEmpty()) {
                    if (workers.contains(worker)) {
                        val innerWorker = workers.firstOrNull {
                            it.username == worker.username && it.password == worker.password
                                    && it.active
                        }
                        if (innerWorker?.active == true) {
                            sharedPref.workerUsername = worker.username
                            val intent = Intent(this, WorkerActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this,
                                "${worker.username} is not activated by admin",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            this,
                            "${worker.username} is not registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.no_worker_registered),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            ROLES.OWNER -> {
                val owner = Owner(username, password)
                if (owners.isNotEmpty()) {
                    if (owners.contains(owner)) {
                        val innerOwner =
                            owners.firstOrNull { it.password == owner.password && it.username == owner.username && it.active }
                        if (innerOwner?.active == true) {
                            sharedPref.ownerUsername = owner.username
                            val intent = Intent(this, OwnerActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "${owner.username} is not activated by admin",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "${owner.username} is not registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.no_owner_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun showCreateAccountDialog() {
        if (::dialogCreateAccount.isInitialized.not()) {
            dialogCreateAccount = AppCompatDialog(this)
            dialogCreateAccount.apply {
                setContentView(R.layout.dialog_create_account)
                setCanceledOnTouchOutside(true)
                setCancelable(false)
                window?.apply {
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setGravity(Gravity.CENTER)
                }
                dialogUsernameInput = this.findViewById(R.id.dialogUsernameInput)!!
                dialogUsernameET = this.findViewById(R.id.dialogUsernameET)!!
                dialogPasswordInput = this.findViewById(R.id.dialogPasswordInput)!!
                dialogPasswordET = this.findViewById(R.id.dialogPasswordET)!!
                this.findViewById<AppCompatImageView>(R.id.closeIV)!!.setOnClickListener {
                    this.cancel()
                }
                this.findViewById<AppCompatButton>(R.id.dialogCreateAccountBTN)!!
                    .setOnClickListener {
                        createNewAccount()
                    }
                dialogUsernameET.doOnTextChanged { _, _, _, _ -> dialogUsernameInput.error = null }
                dialogPasswordET.doOnTextChanged { _, _, _, _ -> dialogPasswordInput.error = null }
            }
        }
        dialogUsernameET.setText("")
        dialogPasswordET.setText("")
        dialogCreateAccount.show()
    }

    private fun createNewAccount() {
        val username = dialogUsernameET.text.toString().trim()
        if (username.isEmpty()) {
            dialogUsernameInput.error = getString(R.string.username_error_empty)
            return
        }
        val password = dialogPasswordET.text.toString().trim()
        if (password.isEmpty()) {
            dialogPasswordInput.error = getString(R.string.password_error_empty)
            return
        }
        if (password.length < 3) {
            dialogPasswordInput.error = getString(R.string.password_error_format)
            return
        }

        when (role) {
            ROLES.ADMIN -> {
                val admin = Admin(username, password)
                Log.e(TAG, "contains ? ${admins.contains(admin)}")
                if (admins.contains(admin)) {
                    Toast.makeText(
                        this,
                        "${admin.username} is already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                dbViewModel.addAdmin(admin)
            }
            ROLES.CUSTOMER -> {
                val customer = Customer(username, password)
                if (customers.contains(customer)) {
                    Toast.makeText(
                        this,
                        "${customer.username} is already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                dbViewModel.addCustomer(customer)
            }
            ROLES.WORKER -> {
                val worker = Worker(username, password)
                if (workers.contains(worker)) {
                    Toast.makeText(
                        this,
                        "${worker.username} is already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                dbViewModel.addWorker(worker)
            }
            ROLES.OWNER -> {
                val owner = Owner(username, password)
                if (owners.contains(owner)) {
                    Toast.makeText(
                        this,
                        "${owner.username} is already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                dbViewModel.addOwner(owner)
            }
        }
        dialogCreateAccount.cancel()
    }

    private fun observeViewModel() {
        dbViewModel.allAdmins?.observe(this) {
            admins.clear()
            it?.let {
                admins.addAll(it)
            }
        }
        dbViewModel.allOwners?.observe(this) {
            owners.clear()
            it?.let {
                owners.addAll(it)
            }
        }
        dbViewModel.allWorkers?.observe(this) {
            workers.clear()
            it?.let {
                workers.addAll(it)
            }
        }
        dbViewModel.allCustomers?.observe(this) {
            customers.clear()
            it?.let {
                customers.addAll(it)
            }
        }
    }


    override fun onStop() {
        super.onStop()
        if (::dialogCreateAccount.isInitialized && dialogCreateAccount.isShowing) dialogCreateAccount.dismiss()
    }
}
