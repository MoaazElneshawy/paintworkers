package com.fatmanaseer.paintapp.presentation.customer.ui.orders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.data.sharedData.SharedPref
import com.fatmanaseer.paintapp.models.orders.Order
import com.fatmanaseer.paintapp.models.reviews.Review

class CustomerOrdersFragment : Fragment(), OnReviewOrderListener {

    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var sharedPref: SharedPref
    private lateinit var ordersAdapter: OrdersAdapter

    private lateinit var noOrdersTV: AppCompatTextView
    private lateinit var ordersRV: RecyclerView

    private lateinit var rateDialog: AppCompatDialog
    private lateinit var addReviewBTN: AppCompatButton
    private lateinit var reviewET: AppCompatEditText
    private lateinit var reviewRate: RatingBar

    private var selectedOrder: Order? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ordersViewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        sharedPref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_customer_orders, container, false)
        noOrdersTV = root.findViewById(R.id.noOrdersTV)
        ordersRV = root.findViewById(R.id.ordersRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        ordersViewModel.getOrders(sharedPref.customerUsername)?.observe(viewLifecycleOwner) {
            it?.let { orders ->
                if (orders.isEmpty()) {
                    noOrdersTV.visibility = View.VISIBLE
                    ordersRV.visibility = View.GONE
                } else {
                    noOrdersTV.visibility = View.GONE
                    ordersAdapter = OrdersAdapter(orders, this)
                    ordersRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@CustomerOrdersFragment.requireContext())
                        adapter = ordersAdapter
                    }
                }
            }
        }
    }

    override fun onReviewOrder(order: Order) {
        if (order.cost == "0") {
            Toast.makeText(requireContext(), "Order doesn\'t have cost !", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (order.hasReview) {
                Toast.makeText(requireContext(), "Order has reviewed before", Toast.LENGTH_SHORT)
                    .show()
            } else {
                selectedOrder = order
                ordersAdapter.notifyDataSetChanged()
                order.hasReview = true
                showReviewRatingDialog()
            }
        }
    }

    private fun showReviewRatingDialog() {
        if (::rateDialog.isInitialized.not()) {
            rateDialog = AppCompatDialog(requireContext())
            rateDialog.apply {
                setContentView(R.layout.dialog_review)
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
                addReviewBTN = findViewById(R.id.dialogAddReviewBTN)!!
                reviewET = findViewById(R.id.dialogReviewET)!!
                reviewRate = findViewById(R.id.dialogReviewRateBarView)!!
                findViewById<AppCompatImageView>(R.id.closeIV)!!.setOnClickListener { this.cancel() }
                addReviewBTN.setOnClickListener { addReview() }
            }
        }
        rateDialog.show()
    }

    private fun addReview() {
        if (selectedOrder!!.cost == "0") {
            Toast.makeText(
                requireContext(),
                "You can rate order after cost approved",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        val reviewText = reviewET.text.toString().trim()
        if (reviewText.isEmpty()) {
            reviewET.error = "Please add review "
            return
        }
        val rate = reviewRate.rating
        if (rate == 0.0f) {
            Toast.makeText(requireContext(), "select rate ", Toast.LENGTH_SHORT).show()
            return
        }
        val review = Review(
            reviews = reviewText,
            workerUsername = selectedOrder!!.workerUsername,
            customerUsername = selectedOrder!!.customerUsername,
            rate = rate
        )
        ordersViewModel.addReview(review)
        rateDialog.dismiss()
    }

    override fun onStop() {
        super.onStop()
        if (::rateDialog.isInitialized && rateDialog.isShowing) rateDialog.cancel()
    }
}