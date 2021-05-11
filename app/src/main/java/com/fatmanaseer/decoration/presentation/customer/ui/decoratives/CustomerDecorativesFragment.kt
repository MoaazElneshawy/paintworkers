package com.fatmanaseer.decoration.presentation.customer.ui.decoratives

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
import com.fatmanaseer.decoration.models.decorative.Decorative
import com.fatmanaseer.decoration.models.requests.Request

class CustomerDecorativesFragment : Fragment(), OnRequestDecorativeListener {

    private lateinit var decorativeViewModel: DecorativesViewModel

    private lateinit var sharedPref: SharedPref

    private lateinit var decorativeAdapter: DecorativeAdapter

    private lateinit var noDecorativeTV: AppCompatTextView
    private lateinit var decorativesRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        decorativeViewModel =
            ViewModelProvider(this).get(DecorativesViewModel::class.java)
        sharedPref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_customer_decoratives, container, false)
        noDecorativeTV = root.findViewById(R.id.noDecorativeTV)
        decorativesRV = root.findViewById(R.id.decorativesRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {

        decorativeViewModel.decorative?.observe(viewLifecycleOwner) {
            it?.let { decorations ->
                if (decorations.isEmpty()) {
                    noDecorativeTV.visibility = View.VISIBLE
                    decorativesRV.visibility = View.GONE
                } else {
                    noDecorativeTV.visibility = View.GONE
                    decorativeAdapter = DecorativeAdapter(decorations, this)
                    decorativesRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@CustomerDecorativesFragment.requireContext())
                        adapter = decorativeAdapter
                    }
                }
            }
        }
    }

    override fun onRequestDecorative(decorative: Decorative) {
        val request = Request(
            type = "decorative",
            customerUsername = sharedPref.customerUsername,
            specifications = decorative.specifications,
            prices = decorative.prices,
            quantities = decorative.quantities,
            ownerName = decorative.ownerName,
            image = decorative.image
        )
        decorativeViewModel.requestDecorative(request)
    }
}