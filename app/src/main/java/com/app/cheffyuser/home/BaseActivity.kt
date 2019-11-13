package com.app.cheffyuser.home

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog

/*

* {@link BaseActivity}
* Add basic network check that's common to all activities
*
 */
open class BaseActivity : AppCompatActivity(), DroidListener {

    var isConnected: Boolean = false
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

    //region Dialogues

    fun showDialogue(title: String = "", descriptions: String): LottieAlertDialog {
        var alertDialog = LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
            .setTitle("Setting you up...")
            .setDescription("Please Wait")
            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
        return alertDialog
    }

    fun dismissDialogue(alertDialog: LottieAlertDialog) {
        alertDialog.dismiss()
    }

    fun errorDialogue(alertDialog: LottieAlertDialog) {
        val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
        alertDialog.changeDialog(builder)
        Handler().postDelayed({ alertDialog.dismiss() }, 3000)
        alertDialog.setCancelable(true)
    }

    fun successDialogue(alertDialog: LottieAlertDialog) {
        val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_SUCCESS)
        alertDialog.changeDialog(builder)
        Handler().postDelayed({ alertDialog.dismiss() }, 3000)
        alertDialog.setCancelable(true)
    }

    fun warningDialogue(alertDialog: LottieAlertDialog) {
        val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_WARNING)
        alertDialog.changeDialog(builder)
        Handler().postDelayed({ alertDialog.dismiss() }, 3000)
        alertDialog.setCancelable(true)
    }

    //endregion

}
