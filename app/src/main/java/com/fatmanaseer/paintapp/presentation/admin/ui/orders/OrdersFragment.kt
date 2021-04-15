package com.fatmanaseer.paintapp.presentation.admin.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R

class OrdersFragment : Fragment() {

    private lateinit var ordersViewModel: OrdersViewModel

    private lateinit var noOrdersTV: AppCompatTextView
    private lateinit var ordersRV: RecyclerView

    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ordersViewModel =
            ViewModelProvider(this).get(OrdersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_orders, container, false)
        noOrdersTV = root.findViewById(R.id.noOrdersTV)
        ordersRV = root.findViewById(R.id.ordersRV)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        ordersViewModel.allOrders?.observe(viewLifecycleOwner) {
            it?.let { orders ->
                if (orders.isEmpty()) {
                    noOrdersTV.visibility = View.VISIBLE
                    ordersRV.visibility = View.GONE
                } else {
                    noOrdersTV.visibility = View.GONE
                    ordersAdapter = OrdersAdapter(orders)
                    ordersRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@OrdersFragment.requireContext())
                        adapter = ordersAdapter
                    }
                }
            }
        }
    }

}