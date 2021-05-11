package com.fatmanaseer.decoration.presentation.customer.ui.specialRequest

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.text.format.DateFormat
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fatmanaseer.decoration.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.munon.turboimageview.TurboImageView
import gun0912.tedbottompicker.TedBottomPicker
import java.io.InputStream
import java.util.*


class SpecialRequestActivity : AppCompatActivity() {

    private lateinit var addSpecialRequestFAB: FloatingActionButton
    private lateinit var doneFAB: FloatingActionButton
    private lateinit var deleteFAB: FloatingActionButton
    private lateinit var selectCameraIV: AppCompatImageView
    private lateinit var armchairIV: AppCompatImageView
    private lateinit var chestIV: AppCompatImageView
    private lateinit var wardrobeIV: AppCompatImageView
    private lateinit var turboImageView: TurboImageView
    private lateinit var furnitureLayout: LinearLayoutCompat
    private lateinit var playground: RelativeLayout

    private val selectedCount = MutableLiveData(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special_request)

        selectCameraIV = findViewById(R.id.specialRequestCameraIV)
        playground = findViewById(R.id.playground)
        armchairIV = findViewById(R.id.armchairIV)
        chestIV = findViewById(R.id.chestIV)
        wardrobeIV = findViewById(R.id.wardrobeIV)
        furnitureLayout = findViewById(R.id.furnitureLayout)
        doneFAB = findViewById(R.id.doneFAB)
        deleteFAB = findViewById(R.id.deleteFAB)
        turboImageView = findViewById(R.id.turboImageView)
        addSpecialRequestFAB = findViewById(R.id.addSpecialRequestFAB)
        turboImageView.objectSelectedBorderColor = getColor(R.color.white)
        turboImageView.isSelectOnObjectAdded = true

        setOnClicks()
        selectedCount.observe(this, Observer {
            it?.let {
                if (it > 0) deleteFAB.visibility = View.VISIBLE
                else deleteFAB.visibility = View.GONE
            }
        })
    }

    private fun setOnClicks() {
        addSpecialRequestFAB.setOnClickListener {
            TedPermission.with(this)
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
        armchairIV.setOnClickListener {
            if (!deleteFAB.isVisible) deleteFAB.visibility = View.VISIBLE
            turboImageView.addObject(this, R.drawable.ic_armchair)
            selectedCount.value = selectedCount.value!! + 1
        }
        wardrobeIV.setOnClickListener {
            if (!deleteFAB.isVisible) deleteFAB.visibility = View.VISIBLE
            turboImageView.addObject(this, R.drawable.ic_wardrobe)
            selectedCount.value = selectedCount.value!! + 1
        }
        chestIV.setOnClickListener {
            if (!deleteFAB.isVisible) deleteFAB.visibility = View.VISIBLE
            turboImageView.addObject(this, R.drawable.ic_chest_of_drawers)
            selectedCount.value = selectedCount.value!! + 1
        }

        deleteFAB.setOnClickListener {
            turboImageView.removeSelectedObject()
            selectedCount.value = selectedCount.value!! - 1
        }
        doneFAB.setOnClickListener {
            TedPermission.with(this)
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {
//                        takeScreenshot()
                        saveImageBitmap(
                            takeScreenshot(
                                window.decorView.findViewById(android.R.id.content)
                            )
                        )
                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    }

                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .check()
        }
    }

    private fun addImage() {
        TedBottomPicker.with(this)
            .showGalleryTile(false)
            .show {
                val imageUri: Uri = it
                val imageStream: InputStream? = this.contentResolver.openInputStream(imageUri)
                selectCameraIV.setImageURI(it)
                if (!furnitureLayout.isVisible) furnitureLayout.visibility = View.VISIBLE
                doneFAB.visibility = View.VISIBLE
          }

    }

    private fun takeScreenshot(v: View): Bitmap {
        v.isDrawingCacheEnabled = true
        v.buildDrawingCache(true)
        val b = Bitmap.createBitmap(v.drawingCache)
        v.isDrawingCacheEnabled = false
        return b
    }

    fun saveImageBitmap(bitmap: Bitmap) {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        val fname = "Image-${SystemClock.currentThreadTimeMillis()}.jpg"
        val imageName = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            fname,
            fname
        )

        if (imageName.isNotEmpty())
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

    }

}