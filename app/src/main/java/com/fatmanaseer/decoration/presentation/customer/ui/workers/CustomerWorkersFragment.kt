package com.fatmanaseer.decoration.presentation.customer.ui.workers

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.data.sharedData.SharedPref
import com.fatmanaseer.decoration.models.orders.Order
import com.fatmanaseer.decoration.models.requests.Request
import com.fatmanaseer.decoration.models.worker.Worker

class CustomerWorkersFragment : Fragment(), OnWorkerClickListener {

    private lateinit var workersViewModel: WorkersViewModel

    private lateinit var workersAdapter: WorkersAdapter
    private lateinit var sharedPref: SharedPref
    private lateinit var noWorkerTV: AppCompatTextView
    private lateinit var workersRV: RecyclerView

    private val customerRequests = ArrayList<Request>()

    private lateinit var requestOrderDialog: AppCompatDialog
    private lateinit var requestOrderSP: AppCompatSpinner
    private lateinit var requestMakeOrderBTN: AppCompatButton

    private var selectedRequest: Request? = null
    private var selectedWorker: Worker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workersViewModel =
            ViewModelProvider(this).get(WorkersViewModel::class.java)
        sharedPref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_customer_workers, container, false)
        noWorkerTV = root.findViewById(R.id.noWorkersTV)
        workersRV = root.findViewById(R.id.workersRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        workersViewModel.workers?.observe(viewLifecycleOwner) {
            it?.let { workers ->
                if (workers.isEmpty()) {
                    noWorkerTV.visibility = View.VISIBLE
                    workersRV.visibility = View.GONE
                } else {
                    noWorkerTV.visibility = View.GONE
                    workersAdapter = WorkersAdapter(workers, this)
                    workersRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@CustomerWorkersFragment.requireContext())
                        adapter = workersAdapter
                    }
                }
            }
        }
        workersViewModel.getRequest(sharedPref.customerUsername)?.observe(viewLifecycleOwner) {
            it?.let {
                customerRequests.clear()
                customerRequests.addAll(it)
            }
        }
    }


    override fun onWorkerClick(worker: Worker) {
        selectedWorker = worker
        if (customerRequests.isEmpty()) {
            Toast.makeText(requireContext(), "Request items first", Toast.LENGTH_LONG).show()
        } else showSelectRequestDialog()
    }

    private fun showSelectRequestDialog() {
        if (::requestOrderDialog.isInitialized.not()) {
            requestOrderDialog = AppCompatDialog(requireContext())
            requestOrderDialog.apply {
                setContentView(R.layout.dialog_request_order_from_worker)
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
                findViewById<AppCompatImageView>(R.id.closeIV)!!.setOnClickListener {
                    this.cancel()
                }
                requestOrderSP = findViewById(R.id.dialogRequestsSP)!!
                requestMakeOrderBTN = findViewById(R.id.dialogRequestsOrderBTN)!!
                requestMakeOrderBTN.setOnClickListener {
                    makeOrderToWorker()
                }
            }
        }
        setSpinner()
        requestOrderDialog.show()
    }

    private fun makeOrderToWorker() {
        if (selectedRequest?.accepted == true) {
            val order = Order(
                orderDetails = selectedRequest!!.specifications,
                customerUsername = sharedPref.customerUsername,
                workerUsername = selectedWorker!!.username
            )
            workersViewModel.makeOrder(order)
        } else {
            Toast.makeText(
                requireContext(),
                "Request is not accepted by Owner",
                Toast.LENGTH_SHORT
            ).show()
        }
        requestOrderDialog.dismiss()
    }

    private fun setSpinner() {
        val titles = ArrayList<String>()
        customerRequests.forEach { titles.add("${it.type}\n${it.specifications}") }
        requestOrderSP.setSelection(0)
        requestOrderSP.apply {
            adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                titles
            )
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedRequest = customerRequests[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (::requestOrderDialog.isInitialized && requestOrderDialog.isShowing) requestOrderDialog.cancel()
    }
}