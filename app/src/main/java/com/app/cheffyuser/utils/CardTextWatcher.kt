package com.app.cheffyuser.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.app.cheffyuser.R


class CardTextWatcher(private val view: View, vararg editTexts: EditText) :
    TextWatcher {
    private val et1: EditText
    private val et2: EditText
    private val et3: EditText
    private val et4: EditText
    private var genericTextWatcherListener: GenericTextWatcherListener? = null

    init {
        et1 = editTexts[0]
        et2 = editTexts[1]
        et3 = editTexts[2]
        et4 = editTexts[3]
    }

    fun setGenericTextWatcherListener(genericTextWatcherListener: GenericTextWatcherListener) {
        this.genericTextWatcherListener = genericTextWatcherListener
    }

    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (view.id) {

            R.id.et1 -> if (text.length == 4)
                et2.requestFocus()
            R.id.et2 -> if (text.length == 4)
                et3.requestFocus()
            else if (text.length == 0)
                et1.requestFocus()
            R.id.et3 -> if (text.length == 4)
                et4.requestFocus()
            else if (text.length == 0)
                et2.requestFocus()
            R.id.et4 -> {
                if (text.length == 0)
                    et3.requestFocus()
            }
        }
        genericTextWatcherListener!!.onFinalClick("" + et1.text+"   " + et2.text+"   " + et3.text+"   " + et4.text)
    }


    override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

    override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

}