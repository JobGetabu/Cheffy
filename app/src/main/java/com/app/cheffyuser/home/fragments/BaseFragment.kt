package com.app.cheffyuser.home.fragments


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.app.cheffyuser.CheffyApp
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog

/**
 * A simple [Fragment] subclass.
 * Add basic network check that's common to all fragments
 *
 */
open class BaseFragment : Fragment(), DroidListener {

    var isConnected: Boolean = false
    val tokenManager = CheffyApp.instance!!.tokenManager
    private lateinit var mDroidNet: DroidNet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDroidNet = DroidNet.getInstance()
        mDroidNet.addInternetConnectivityListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity)
    }

    override fun onInternetConnectivityChanged(isCon: Boolean) {
        isConnected = isCon
    }

    override fun onDestroy() {
        super.onDestroy()
        mDroidNet.removeInternetConnectivityChangeListener(this)
    }

    //region Dialogues

    fun showDialogue(
        title: String = "",
        descriptions: String = "Please wait..."
    ): LottieAlertDialog? {
        activity?.let {

            var alertDialog = LottieAlertDialog.Builder(it, DialogTypes.TYPE_LOADING)
                .setTitle(title)
                .setDescription(descriptions)
                .build()
            alertDialog.setCancelable(false)
            alertDialog.show()
            return alertDialog
        }
        return null
    }

    fun dismissDialogue(alertDialog: LottieAlertDialog) {
        alertDialog.dismiss()
    }

    fun errorDialogue(
        title: String = "Error",
        descriptions: String = "",
        alertDialog: LottieAlertDialog?
    ) {
        activity?.let {
            val builder = LottieAlertDialog.Builder(it, DialogTypes.TYPE_ERROR)
                .setTitle(title)
                .setDescription(descriptions)
            alertDialog?.changeDialog(builder)
            Handler().postDelayed({ alertDialog?.dismiss() }, 3000)
            alertDialog?.setCancelable(true)
        }
    }

    fun successDialogue(
        title: String = "Success",
        descriptions: String = "",
        alertDialog: LottieAlertDialog?
    ) {
        activity?.let {
            val builder = LottieAlertDialog.Builder(it, DialogTypes.TYPE_SUCCESS)
                .setTitle(title)
                .setDescription(descriptions)
            alertDialog?.changeDialog(builder)
            Handler().postDelayed({ alertDialog?.dismiss() }, 3000)
            alertDialog?.setCancelable(true)
        }
    }

    fun warningDialogue(
        title: String = "Warning",
        descriptions: String = "",
        alertDialog: LottieAlertDialog?
    ) {
        activity?.let {
            val builder = LottieAlertDialog.Builder(it, DialogTypes.TYPE_WARNING)
                .setTitle(title)
                .setDescription(descriptions)
            alertDialog?.changeDialog(builder)
            Handler().postDelayed({ alertDialog?.dismiss() }, 3000)
            alertDialog?.setCancelable(true)
        }
    }

    //endregion

}
