package com.app.cheffyuser.home.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.home.fragments.NoNetListener
import com.app.cheffyuser.home.fragments.NoNetworkDialogue
import com.app.cheffyuser.utils.toast
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
        Timber.d("Base net state=> $isConnected")
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


    fun getMyColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(this, color)
    }

    fun getMyDrawable(@DrawableRes drawable: Int): Drawable {
        return ContextCompat.getDrawable(this, drawable)!!
    }

    fun checkNetwork() {

        val fragmentManager = supportFragmentManager
        val newFragment = NoNetworkDialogue()
        newFragment.setOnNetListener(object : NoNetListener {
            override fun onNetComeBack() {
                toast("TODO: test if Recreate this activity is actually necessary")
                //recreate()
            }
        })

        try {
            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()

        } catch (e: IllegalStateException) {
            //Occurs on very fast switching
            Timber.e(e)
        }
    }
}
