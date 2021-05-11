package com.fatmanaseer.decoration.presentation.owner.ui.requests

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
import com.fatmanaseer.decoration.models.requests.Request

class RequestsFragment : Fragment(), OnAcceptRequestListener {

    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var requestsAdapter: RequestsAdapter
    private lateinit var sharedPref: SharedPref

    private lateinit var noRequestsTV: AppCompatTextView
    private lateinit var requestsRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestsViewModel =
            ViewModelProvider(this).get(RequestsViewModel::class.java)
        sharedPref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_requests, container, false)
        noRequestsTV = root.findViewById(R.id.noRequestsTV)
        requestsRV = root.findViewById(R.id.requestsRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        requestsViewModel.getRequest(sharedPref.ownerUsername)?.observe(viewLifecycleOwner) {
            it?.let { requests ->
                if (requests.isEmpty()) {
                    noRequestsTV.visibility = View.VISIBLE
                    requestsRV.visibility = View.GONE
                } else {
                    noRequestsTV.visibility = View.GONE
                    requestsAdapter = RequestsAdapter(requests, this)
                    requestsRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@RequestsFragment.requireContext())
                        adapter = requestsAdapter
                    }
                }
            }
        }
    }

    override fun onAcceptRequest(request: Request) {
        requestsViewModel.acceptRequest(request.id)
    }

}