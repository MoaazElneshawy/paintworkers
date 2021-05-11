package com.fatmanaseer.decoration.presentation.owner.ui.wallPapers

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.data.sharedData.SharedPref
import com.fatmanaseer.decoration.models.wallpapers.Wallpaper
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import java.io.ByteArrayOutputStream
import java.io.InputStream


class WallPapersFragment : Fragment(), OnDeleteWallpaperListener {

    private val TAG = WallPapersFragment::class.java.simpleName

    private lateinit var wallPapersViewModel: WallPapersViewModel
    private lateinit var sharedPref: SharedPref
    private lateinit var wallpapersAdapter: WallpapersAdapter

    private lateinit var priceET: AppCompatEditText
    private lateinit var quantityET: AppCompatEditText
    private lateinit var specET: AppCompatEditText
    private lateinit var addWallpaperBTN: AppCompatButton
    private lateinit var addWallpaperImageBTN: AppCompatButton
    private lateinit var noWallpapersTV: AppCompatTextView
    private lateinit var wallpapersRV: RecyclerView
    private lateinit var imageSelectedTV: AppCompatTextView

    private var wallpaperImage = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wallPapersViewModel =
            ViewModelProvider(this).get(WallPapersViewModel::class.java)
        sharedPref = SharedPref(requireContext())

        val root = inflater.inflate(R.layout.fragment_wallpapers, container, false)

        priceET = root.findViewById(R.id.wallpaperPriceET)
        quantityET = root.findViewById(R.id.wallpaperQuantityET)
        specET = root.findViewById(R.id.wallpaperSpecET)
        addWallpaperBTN = root.findViewById(R.id.addWallpaperBTN)
        addWallpaperImageBTN = root.findViewById(R.id.addWallpaperImageBTN)
        imageSelectedTV = root.findViewById(R.id.imageSelectedTV)
        noWallpapersTV = root.findViewById(R.id.noWallpapersTV)
        wallpapersRV = root.findViewById(R.id.wallpapersRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
        observeViewModel()
    }

    private fun setOnClicks() {
        addWallpaperImageBTN.setOnClickListener {
            TedPermission.with(requireContext())
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {
                        addImage()
                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    }

                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .check()
        }
        addWallpaperBTN.setOnClickListener { addNewWallpaper() }
    }

    private fun addNewWallpaper() {
        val price: Double
        val quantity: Int
        val spec = specET.text.toString().trim()
        if (priceET.text.toString().trim().isNotEmpty()) {
            price = priceET.text.toString().trim().toDouble()
        } else {
            priceET.error = getString(R.string.price_error)
            return
        }
        if (quantityET.text.toString().trim().isNotEmpty()) {
            quantity = quantityET.text.toString().trim().toInt()
        } else {
            quantityET.error = getString(R.string.quantity_error)
            return
        }
        if (spec.isEmpty()) {
            specET.error = getString(R.string.specifications_error)
            return
        }
        wallPapersViewModel.insertWallpaper(
            Wallpaper(
                spec,
                quantity,
                price,
                sharedPref.ownerUsername,
                wallpaperImage
            )
        )
        wallpaperImage = ""
        imageSelectedTV.text = "No image selected"
        priceET.setText("")
        quantityET.setText("")
        specET.setText("")
    }

    private fun addImage() {
        TedBottomPicker.with(this.requireActivity())
            .show {
                val imageUri: Uri = it
                val imageStream: InputStream? =
                    requireActivity().contentResolver.openInputStream(imageUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                wallpaperImage = encodeImage(selectedImage) ?: ""
                if (wallpaperImage.isNotEmpty()) imageSelectedTV.text = "Image selected"
            }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun observeViewModel() {
        wallPapersViewModel.getWallpapers(sharedPref.ownerUsername)?.observe(viewLifecycleOwner) {
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
                            LinearLayoutManager(this@WallPapersFragment.requireContext())
                        adapter = wallpapersAdapter
                    }
                }
            }
        }
    }

    override fun onDelete(wallpaper: Wallpaper) {
        wallPapersViewModel.deleteWallpaper(wallpaper)
    }
}