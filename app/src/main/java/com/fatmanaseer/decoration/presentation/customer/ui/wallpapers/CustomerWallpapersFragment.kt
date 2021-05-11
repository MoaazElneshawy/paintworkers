package com.fatmanaseer.decoration.presentation.customer.ui.wallpapers

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
import com.fatmanaseer.decoration.models.wallpapers.Wallpaper
import com.fatmanaseer.decoration.presentation.customer.ui.workers.OnRequestWallpaperListener
import com.fatmanaseer.decoration.presentation.customer.ui.workers.WallpapersAdapter

class CustomerWallpapersFragment : Fragment(), OnRequestWallpaperListener {

    private lateinit var wallpapersViewModel: WallpapersViewModel
    private lateinit var sharedPref: SharedPref

    private lateinit var wallpapersAdapter: WallpapersAdapter

    private lateinit var noWallpapersTV: AppCompatTextView
    private lateinit var wallpapersRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wallpapersViewModel = ViewModelProvider(this).get(WallpapersViewModel::class.java)
        sharedPref = SharedPref(requireContext())
        val root = inflater.inflate(R.layout.fragment_customer_wallpapers, container, false)
        noWallpapersTV = root.findViewById(R.id.noWallpapersTV)
        wallpapersRV = root.findViewById(R.id.wallpapersRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        wallpapersViewModel.wallpapers?.observe(viewLifecycleOwner) {
            it?.let { wallpapers ->
                if (wallpapers.isEmpty()) {
                    noWallpapersTV.visibility = View.VISIBLE
                    wallpapersRV.visibility = View.GONE
                } else {
                    noWallpapersTV.visibility = View.GONE
                    wallpapersAdapter = WallpapersAdapter(wallpapers, this)
                    wallpapersRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@CustomerWallpapersFragment.requireContext())
                        adapter = wallpapersAdapter
                    }
                }
            }
        }
    }

    override fun onRequestWallpaper(wallpaper: Wallpaper) {
        val request = Request(
            type = "wallpaper",
            customerUsername = sharedPref.customerUsername,
            specifications = wallpaper.specifications,
            prices = wallpaper.prices,
            quantities = wallpaper.quantities,
            ownerName = wallpaper.ownerName,
            image = wallpaper.image
        )
        wallpapersViewModel.requestWallpaper(request)
    }


}