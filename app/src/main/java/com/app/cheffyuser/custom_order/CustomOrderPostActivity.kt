package com.app.cheffyuser.custom_order

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.adapter.RecyclerItemClickListener
import com.app.cheffyuser.home.adapter.UploadImageAdapter
import com.app.cheffyuser.home.model.CreateCustomRequest
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.PickerInterface
import com.app.cheffyuser.utils.Tools
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.toast
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import com.fxn.utility.PermUtil
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import kotlinx.android.synthetic.main.activity_custom_order_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CustomOrderPostActivity : BaseActivity(), PickerInterface {

    private var miles = 3.0

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, CustomOrderPostActivity::class.java)

        private const val GALLERY_REQUEST_CODE = 143
        private const val CAMERA_REQUEST_CODE = 175
        private const val PIX_REQUEST_CODE = 100
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private lateinit var options: Options

    private lateinit var pickerInterface: PickerInterface
    private lateinit var adapter: UploadImageAdapter

    private var cameraFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_order_post)

        pickerInterface = this

        uiStuff()
    }

    private fun uiStuff() {


        btn_post_order.setOnClickListener {

            saveCustomOrder()
        }

        uploadpicture.setOnClickListener {
            //val sheet = PickerBottomSheet(pickerInterface)
            //sheet.show(supportFragmentManager, PickerBottomSheet.TAG)

            launchPhotoPicker()
        }

        distProgress.incrementProgressBy(1)
        distProgress.progress = 3
        distProgress.setOnSeekBarChangeListener(seekBarChangeListener)

    }

    private val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {

        var seekV = 0

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            seekV = progress
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

            miles = seekV * 1.0
            distTxt.text = "$miles"

            if (seekV == 0) {
                miles = 0.5
                distTxt.text = "$miles"
            }
        }

    }

    private fun setUpUploadList() {
        upload_list.setHasFixedSize(true)

        adapter = UploadImageAdapter(
            this, vm.imagesUrls,
            object : RecyclerItemClickListener {
                override fun modelClick(model: Any) {
                    model as String
                    //remove item from list
                    vm.imagesUrls?.remove(model)
                    adapter.refreshList()
                }
            })

        upload_list.adapter = adapter
    }

    //region TAKE PHOTO LOGIC

    override fun picked(isCamera: Boolean) {
        if (isCamera) Tools.captureFromCameraWithPerm(
            this,
            { createImageFile() },
            CAMERA_REQUEST_CODE
        )
        else Tools.pickFromGallery(this, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                val selectedImage = data?.data
                if (selectedImage != null) {
                    adapter.refreshList()
                }
            }
            CAMERA_REQUEST_CODE -> {
                if (cameraFilePath != null) {
                    adapter.refreshList()
                }
            }

            PIX_REQUEST_CODE -> {
                vm.imagesUrls = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                Timber.d("$vm.imagesUrls")
                setUpUploadList()
                adapter.refreshList()
            }

            else -> {
                Timber.d("Nothing")
            }
        }
    }


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

    private fun launchPhotoPicker() {
        options = Options.init()
            .setRequestCode(PIX_REQUEST_CODE)
            .setCount(6)
            .setFrontfacing(false)
            .setImageQuality(ImageQuality.HIGH)
            .setPreSelectedUrls(vm.imagesUrls)
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("/Cheffy")

        Pix.start(this, options)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchPhotoPicker()
                } else {
                    toast("Grant Cheffy permissions to open Gallery", Toast.LENGTH_LONG)
                }

                return
            }
        }
    }

    //endregion


    private fun saveCustomOrder() {

        val foodname = etFoodName.text.toString()
        val fooddescription = etFoodDescription.text.toString()
        val minPrice = etMinPrice.text.toString().trim().replace("$", "")
        val maxPrice = etMaxPrice.text.toString().trim().replace("$", "")

        if (foodname.isEmpty() && fooddescription.isEmpty() && minPrice.isEmpty() && maxPrice.isEmpty()) {
            createSnack(ctx = this, txt = "All fields are required")
            return
        }

        if (vm.imagesUrls!!.isEmpty()) {
            createSnack(ctx = this, txt = "Upload at least one image")
            return
        }

        if (!isConnected) {
            createSnack(
                this, getString(R.string.you_not_connected), getString(R.string.retry),
                View.OnClickListener { saveCustomOrder() })
            return
        }


        val dialog = showDialogue("Posting your order", "Please wait ...")

        val createCustomRequest = CreateCustomRequest(
            foodname,
            fooddescription,
            minPrice.toDouble(),
            maxPrice.toDouble(),
            1,
            miles
        )

        vm.createCustomPlate(createCustomRequest).observe(this, androidx.lifecycle.Observer {

            val data = it.data

            when (it.status) {
                Status.ERROR -> {
                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Debug: Error ${it.message}")

                    errorDialogue("Error", "${data?.message}", dialog)
                }
                Status.SUCCESS -> {

                    //prepare image download
                    val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
                        .setTitle("Uploading images")

                    dialog.setCancelable(true)
                    dialog.changeDialog(builder)
                    dialog.setOnCancelListener {
                        toast("Uploading...")
                    }

                    val filesToUpload = mutableListOf<MultipartBody.Part>()

                    vm.imagesUrls?.forEach { path ->
                        val file = File(path)
                        if (file.exists()) {

                            val mFile: RequestBody =
                                RequestBody.create(MediaType.parse("image/*"), file)
                            val fileToUpload: MultipartBody.Part =
                                MultipartBody.Part.createFormData(
                                    "custom_plate_image",
                                    file.name,
                                    mFile
                                )

                            filesToUpload.add(fileToUpload)

                        }
                    }

                    if (filesToUpload.isNullOrEmpty()){
                        if (BuildConfig.DEBUG)
                            createSnack(ctx = this, txt = "Debug: null upload images")
                    }

                    val customplate = it.data?.data?.plate!!

                    vm.uploadCustomPlateImages(customplate.id!!, filesToUpload)
                        .observe(this, androidx.lifecycle.Observer {
                            when (it.status) {
                                Status.ERROR -> {
                                    if (BuildConfig.DEBUG)
                                        createSnack(ctx = this, txt = "Debug: Error ${it.message}")
                                    errorDialogue("Error", "${data?.message}", dialog)
                                    toast("Error uploading...")
                                }

                                Status.SUCCESS -> {

                                    successDialogue(
                                        alertDialog = dialog,
                                        descriptions = "${data?.message}"
                                    )

                                    val intent = Intent(
                                        this@CustomOrderPostActivity,
                                        OrderCompleteActivity::class.java
                                    )
                                    startActivity(intent)
                                    finish()
                                }

                                else -> {
                                    dialog?.dismiss()
                                }
                            }
                        })
                }

                Status.LOADING -> {
                }
            }
        })
    }
}
