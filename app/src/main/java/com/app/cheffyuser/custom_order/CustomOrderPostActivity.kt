package com.app.cheffyuser.custom_order

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.utils.PickerBottomSheet
import com.app.cheffyuser.utils.PickerInterface
import com.app.cheffyuser.utils.Tools
import kotlinx.android.synthetic.main.activity_custom_order_post.*
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CustomOrderPostActivity : BaseActivity(), PickerInterface {

    private val GALLERY_REQUEST_CODE = 143
    private val CAMERA_REQUEST_CODE = 175

    private lateinit var pickerInterface: PickerInterface
    private var cameraFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_order_post)

        pickerInterface = this

        btn_post_order.setOnClickListener {
            val intent = Intent(this@CustomOrderPostActivity, OrderCompleteActivity::class.java)
            startActivity(intent)
        }

        uploadpicture.setOnClickListener {
            val sheet = PickerBottomSheet(pickerInterface)
            sheet.show(supportFragmentManager, PickerBottomSheet.TAG)
        }
    }


    override fun picked(isCamera: Boolean) {
        if (isCamera) Tools.captureFromCameraWithPerm(this, { createImageFile() }, CAMERA_REQUEST_CODE)
        else Tools.pickFromGallery(this, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                val selectedImage = data!!.data
                if(selectedImage != null){

                    //TODO
                    //image_got.setImageURI(selectedImage)
                    //toUpload()
                }
            }
            CAMERA_REQUEST_CODE -> {
                if(cameraFilePath != null){

                    //TODO
                    //image_got.setImageURI(selectedImage)
                    //toUpload()
                }
            }
            else -> {
                Timber.d("Nothing")
            }
        }
    }
    //region TAKE PHOTO LOGIC

    private fun createImageFile(): File? {
        val mediaStorageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Cheffy")

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val image = File(
            mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg"
        )

        // Save a file: path for using again
        cameraFilePath = "file://" + image.absolutePath
        return image
    }

    //endregion


}
