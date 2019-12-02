package com.app.cheffyuser.home.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.cheffyuser.CheffyApp
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import timber.log.Timber

/*

* {@link BaseActivity}
* Add basic network check that's common to all activities
*
 */
open class BaseActivity : AppCompatActivity(), DroidListener {

    var isConnected: Boolean = false
    val tokenManager = CheffyApp.instance!!.tokenManager

    private lateinit var mDroidNet: DroidNet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDroidNet = DroidNet.getInstance()
        mDroidNet.addInternetConnectivityListener(this)
    }

    override fun onInternetConnectivityChanged(isCon: Boolean) {
        isConnected = isCon
    }

    override fun onDestroy() {
        super.onDestroy()
        mDroidNet.removeInternetConnectivityChangeListener(this)
    }

    fun close(v: View) {
        Timber.i("$v")
        onBackPressed()
    }

    //region Dialogues

    fun showDialogue(title: String = "", descriptions: String = "Please wait..."): LottieAlertDialog {
        var alertDialog = LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
            .setTitle(title)
            .setDescription(descriptions)
            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
        return alertDialog
    }

    fun dismissDialogue(alertDialog: LottieAlertDialog?) {
        alertDialog?.dismiss()
    }

    fun errorDialogue(title: String = "Error", descriptions: String = "", alertDialog: LottieAlertDialog?) {
        val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
            .setTitle(title)
            .setDescription(descriptions)
        alertDialog?.changeDialog(builder)
        Handler().postDelayed({ alertDialog?.dismiss() }, 3000)
        alertDialog?.setCancelable(true)
    }

    fun successDialogue(title: String = "Success", descriptions: String = "", alertDialog: LottieAlertDialog?) {
        val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_SUCCESS)
            .setTitle(title)
            .setDescription(descriptions)
        alertDialog?.changeDialog(builder)
        Handler().postDelayed({ alertDialog?.dismiss() }, 1000)
        alertDialog?.setCancelable(true)
    }

    fun warningDialogue(title: String = "Warning", descriptions: String = "", alertDialog: LottieAlertDialog?) {
        val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_WARNING)
            .setTitle(title)
            .setDescription(descriptions)
        alertDialog?.changeDialog(builder)
        Handler().postDelayed({ alertDialog?.dismiss() }, 3000)
        alertDialog?.setCancelable(true)
    }

    //endregion

    fun hideSystemNavOnly(){
        window.decorView.systemUiVisibility = (
               View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }

    fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }


    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}
