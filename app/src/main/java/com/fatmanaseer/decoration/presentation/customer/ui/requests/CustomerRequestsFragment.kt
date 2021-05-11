package com.fatmanaseer.decoration.presentation.customer.ui.requests

import android.content.Intent
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
import com.fatmanaseer.decoration.presentation.customer.ui.specialRequest.SpecialRequestActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CustomerRequestsFragment : Fragment() {

    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var requestsAdapter: RequestsAdapter
    private lateinit var sharedPref: SharedPref

    private lateinit var noRequestsTV: AppCompatTextView
    private lateinit var addRequsetFab: FloatingActionButton
    private lateinit var requestsRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestsViewModel =
            ViewModelProvider(this).get(RequestsViewModel::class.java)
        sharedPref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_customer_requests, container, false)
        noRequestsTV = root.findViewById(R.id.noRequestsTV)
        addRequsetFab = root.findViewById(R.id.addRequestFAB)
        requestsRV = root.findViewById(R.id.requestsRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnClicks()
    }

    private fun setOnClicks() {
        addRequsetFab.setOnClickListener {
            val intent = Intent(requireContext(), SpecialRequestActivity::class.java)
            startActivity(intent)
        }
    }


    private fun observeViewModel() {
        requestsViewModel.getRequest(sharedPref.customerUsername)?.observe(viewLifecycleOwner) {
            it?.let { requests ->
                if (requests.isEmpty()) {
                    noRequestsTV.visibility = View.VISIBLE
                    requestsRV.visibility = View.GONE
                } else {
                    noRequestsTV.visibility = View.GONE
                    requestsAdapter = RequestsAdapter(requests)
                    requestsRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@CustomerRequestsFragment.requireContext())
                        adapter = requestsAdapter
                    }
                }
            }
        }
    }

}