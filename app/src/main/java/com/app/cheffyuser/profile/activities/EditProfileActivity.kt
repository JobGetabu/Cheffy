package com.app.cheffyuser.profile.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.BuildConfig
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.activities.ForgotPasswordActivity
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.app.cheffyuser.utils.loadUrl
import com.app.cheffyuser.utils.toast
import com.bumptech.glide.Glide
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import com.fxn.utility.PermUtil
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_edit_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File

class EditProfileActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, EditProfileActivity::class.java)

        private const val PIX_REQUEST_CODE = 100
        private const val IMAGE_COMPRESSION = 80
        private var lockAspectRatio: Boolean = false
        private var setBitmapMaxWidthHeight = false
        private var ASPECT_RATIO_X = 16
        private var ASPECT_RATIO_Y = 9
        private var bitmapMaxWidth = 1000
        private var bitmapMaxHeight = 1000
    }

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        uiStuff()

    }

    private fun uiStuff() {
        btn_change_pss.setOnClickListener {

            startActivity(ForgotPasswordActivity.newIntent(this))
        }

        val user = tokenManager.userData

        tv_user_name.text = user?.name
        tv_phone.setText(user?.phoneNo)
        etxt_email.setText(user?.email)

        if (tokenManager.shippingData != null) {
            val ar = tokenManager.shippingData
            val address = "${ar?.addressLine1} \n${ar?.addressLine2} \n" +
                    "${ar?.city} ${ar?.state} \n" +
                    "Zipcode: ${ar?.zipCode}"

            etxt_address.setText(address)
        }

        //Loading profile
        user_image.loadUrl(user?.imagePath, R.drawable.avatar_placeholder)
        user_image.setOnClickListener {
            launchPhotoPicker()
        }

        upload_image.setOnClickListener {
            launchPhotoPicker()
        }

        etxt_address.setOnClickListener {
            val intent = Intent(this, ShippingActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Timber.d("result code =>$resultCode")
        when (requestCode) {

            PIX_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                vm.imagesUrls = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)

                try {

                    val file = File(vm.imagesUrls!![0])
                    if (file.exists()) {


                        val fName = System.currentTimeMillis().toString() + ".jpg"
                        //send to cropper
                        val imUri = Uri.fromFile(file)
                        cropImage(imUri, fName)

                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }

            UCrop.REQUEST_CROP -> if (resultCode == Activity.RESULT_OK)  {
                handleUCropResult(data)
            }

            UCrop.RESULT_ERROR -> {
                val cropError = UCrop.getError(data!!)
                Timber.e(cropError, "Crop error: ")
                setResultCancelled()
            }

            else -> setResultCancelled()

        }
    }

    private fun toUploadPic(fileToUpload: MultipartBody.Part) {

        val dialog = showDialogue("Uploading profile pic", "Please wait ...")

        //TODO: push shipping data to server
        vm.uploadProfile(fileToUpload).observe(this, Observer {

            val data = it.data

            when (it.status) {
                Status.ERROR -> {
                    if (BuildConfig.DEBUG)
                        createSnack(ctx = this, txt = "Error uploading profile")

                    errorDialogue("Error", "${data?.message}", dialog)
                    //checkNetwork()
                }
                Status.SUCCESS -> {

                    successDialogue(alertDialog = dialog, descriptions = "${data?.message}")
                    finish()
                }
                Status.LOADING -> {

                }
            }
        })

    }

    private fun launchPhotoPicker() {
        options = Options.init()
            .setRequestCode(PIX_REQUEST_CODE)
            .setCount(1)
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

    private fun cropImage(sourceUri: Uri?, fName: String) {
        val destinationUri = Uri.fromFile(File(cacheDir, fName))

        val options = UCrop.Options()
        options.setCompressionQuality(IMAGE_COMPRESSION)
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary))

        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X.toFloat(), ASPECT_RATIO_Y.toFloat())

        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight)

        UCrop.of(sourceUri!!, destinationUri)
            .withOptions(options)
            .start(this@EditProfileActivity)
    }

    private fun handleUCropResult(data: Intent?) {
        if (data == null) {
            setResultCancelled()
            return
        }
        val resultUri = UCrop.getOutput(data)
        setResultOk(resultUri)
    }

    private fun setResultOk(imagePath: Uri?) {

        val file = File(imagePath!!.path)
        if (file.exists()) {

            val myBitmap = BitmapFactory.decodeFile(file.absolutePath)

            //TODO: test upload here
            Glide.with(this)
                .asBitmap()
                .load(myBitmap)
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_placeholder)
                .into(user_image)


            val mFile: RequestBody =
                RequestBody.create(MediaType.parse("image/*"), file)
            val fileToUpload: MultipartBody.Part =
                MultipartBody.Part.createFormData("profile_photo", file.name, mFile)


            toUploadPic(fileToUpload)
        }
    }

    private fun setResultCancelled() {
        Timber.d("Nothing")
        toast("Cancelled")
    }

}
