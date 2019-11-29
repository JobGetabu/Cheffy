package com.app.cheffyuser.home.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.utils.createSnack
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import timber.log.Timber

class NoNetworkDialogue : DialogFragment(), DroidListener {

    private var root_view: View? = null
    private var nonet_btn: Button? = null
    private var titleTxt: TextView? = null
    private var des: TextView? = null
    private var mDroidNet: DroidNet? = null
    private var isCon: Boolean? = false
    private var noNetListener: NoNetListener? = null

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root_view = inflater.inflate(R.layout.no_net_layout, container, false)
        nonet_btn = root_view!!.findViewById(R.id.btn_try)
        titleTxt = root_view!!.findViewById(R.id.title)
        des = root_view!!.findViewById(R.id.description)

        //init
        mDroidNet = DroidNet.getInstance()
        mDroidNet!!.addInternetConnectivityListener(this)

        nonet_btn!!.setOnClickListener {

            if (isCon!!) {
                dismiss()
                noNetListener!!.onNetComeBack()
            } else
                createSnack(activity!!, txt = activity?.getString(R.string.you_not_connected)!!)


            // add logic to call server again

        }

        return root_view
    }

    fun setOnNetListener(noNetListener: NoNetListener) {
        this.noNetListener = noNetListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        titleTxt?.text = vm.tt
        des?.text = vm.desc
        nonet_btn?.text = vm.actionTxt
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        isCon = isConnected
        if (isConnected) {


            /*if (isAdded) {
                if (isVisible) {
                    try {
                        this?.dismiss()
                    } catch (x: IllegalStateException) {
                        Timber.e(x)
                    }
                }
            }*/


            try {
                this?.dismiss()
            } catch (x: Exception) {
                Timber.e(x)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDroidNet!!.removeInternetConnectivityChangeListener(this)
    }
}
