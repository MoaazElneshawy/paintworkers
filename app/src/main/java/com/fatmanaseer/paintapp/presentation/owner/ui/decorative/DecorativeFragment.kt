package com.fatmanaseer.paintapp.presentation.owner.ui.decorative

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
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.data.sharedData.SharedPref
import com.fatmanaseer.paintapp.models.decorative.Decorative
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import java.io.ByteArrayOutputStream
import java.io.InputStream

class DecorativeFragment : Fragment(), OnDeleteDecorativeListener {

    private val TAG = DecorativeFragment::class.java.simpleName

    private lateinit var decorativeViewModel: DecorativeViewModel

    private lateinit var sharedPref: SharedPref

    private lateinit var decorationsAdapter: DecorationsAdapter

    private lateinit var priceET: AppCompatEditText
    private lateinit var quantityET: AppCompatEditText
    private lateinit var specET: AppCompatEditText
    private lateinit var addDecorativeBTN: AppCompatButton
    private lateinit var addDecorativeImageBTN: AppCompatButton
    private lateinit var noDecorativeTV: AppCompatTextView
    private lateinit var imageSelectedTV: AppCompatTextView
    private lateinit var decorativesRV: RecyclerView

    private var decorativeImage = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        decorativeViewModel =
            ViewModelProvider(this).get(DecorativeViewModel::class.java)
        sharedPref = SharedPref(requireContext())

        val root = inflater.inflate(R.layout.fragment_decorative, container, false)

        priceET = root.findViewById(R.id.decorativePriceET)
        quantityET = root.findViewById(R.id.decorativeQuantityET)
        specET = root.findViewById(R.id.decorativeSpecET)
        addDecorativeBTN = root.findViewById(R.id.addDecorativeBTN)
        addDecorativeImageBTN = root.findViewById(R.id.addDecorativeImageBTN)
        imageSelectedTV = root.findViewById(R.id.imageSelectedTV)
        noDecorativeTV = root.findViewById(R.id.noDecorativeTV)
        decorativesRV = root.findViewById(R.id.decorativesRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnClicks()
    }


    private fun setOnClicks() {
        addDecorativeImageBTN.setOnClickListener {
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
        addDecorativeBTN.setOnClickListener { addNewDecorative() }
    }

    private fun addNewDecorative() {
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
        decorativeViewModel.insertDecorative(
            Decorative(
                spec,
                quantity,
                price,
                sharedPref.ownerUsername,
                decorativeImage
            )
        )
        decorativeImage = ""
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
                decorativeImage = encodeImage(selectedImage) ?: ""
                if (decorativeImage.isNotEmpty()) imageSelectedTV.text = "Image selected"
            }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun observeViewModel() {

        decorativeViewModel.getDecoration(sharedPref.ownerUsername)?.observe(viewLifecycleOwner) {
            it?.let { decorations ->
                if (decorations.isEmpty()) {
                    noDecorativeTV.visibility = View.VISIBLE
                    decorativesRV.visibility = View.GONE
                } else {
                    noDecorativeTV.visibility = View.GONE
                    decorationsAdapter = DecorationsAdapter(decorations, this)
                    decorativesRV.apply {
                        visibility = View.VISIBLE
                        layoutManager =
                            LinearLayoutManager(this@DecorativeFragment.requireContext())
                        adapter = decorationsAdapter
                    }
                }
            }
        }
    }

    override fun onDelete(decoration: Decorative) {
        decorativeViewModel.deleteDecorative(decoration)
    }


}