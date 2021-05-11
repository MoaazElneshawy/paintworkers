package com.fatmanaseer.decoration.presentation.worker.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.data.sharedData.SharedPref
import com.fatmanaseer.decoration.models.orders.Order

class WorkerOrdersFragment : Fragment(), OnOrderCostUpdateListener {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var sharePref: SharedPref
    private lateinit var ordersAdapter: OrdersAdapter

    private lateinit var noOrdersTV: AppCompatTextView
    private lateinit var ordersRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderViewModel =
            ViewModelProvider(this).get(OrderViewModel::class.java)
        sharePref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_worker_orders, container, false)
        noOrdersTV = root.findViewById(R.id.noOrdersTV)
        ordersRV = root.findViewById(R.id.ordersRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        orderViewModel.getMyOrders(sharePref.workerUsername)?.observe(viewLifecycleOwner) {
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
                            LinearLayoutManager(this@WorkerOrdersFragment.requireContext())
                        adapter = ordersAdapter
                    }
                }
            }
        }
    }

    override fun updateCost(cost: String, order: Order) {
        order.cost = cost
        orderViewModel.updateCost(order)
    }
}