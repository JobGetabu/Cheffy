package com.app.cheffyuser.cart.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.app.cheffyuser.R
import com.app.cheffyuser.home.activities.BaseActivity
import com.app.cheffyuser.home.fragments.SheetGiveTip
import kotlinx.android.synthetic.main.tip_layout.*

class PaymentActivity : BaseActivity() {

    private var tipAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        uiStuff()
    }

    private fun uiStuff() {

        txt_no_tip.setOnClickListener { tipClicks(txt_no_tip) }
        txt_tips_1.setOnClickListener { tipClicks(txt_tips_1) }
        txt_tips_2.setOnClickListener { tipClicks(txt_tips_2) }
        txt_tips_5.setOnClickListener { tipClicks(txt_tips_5) }
        txt_tips_other.setOnClickListener { tipClicks(txt_tips_other) }
    }


    private fun tipClicks(v: View) {

        txt_no_tip.setBackgroundColor(getMyColor(R.color.white))
        txt_no_tip.setTextColor(getMyColor(R.color.realblack))
        txt_tips_1.setBackgroundColor(getMyColor(R.color.white))
        txt_tips_1.setTextColor(getMyColor(R.color.realblack))
        txt_tips_2.setBackgroundColor(getMyColor(R.color.white))
        txt_tips_2.setTextColor(getMyColor(R.color.realblack))
        txt_tips_5.setBackgroundColor(getMyColor(R.color.white))
        txt_tips_5.setTextColor(getMyColor(R.color.realblack))
        txt_tips_other.setBackgroundColor(getMyColor(R.color.white))
        txt_tips_other.setTextColor(getMyColor(R.color.realblack))


        v.setBackgroundColor(getMyColor(R.color.colorAccent))
        v as TextView
        v.setTextColor(getMyColor(R.color.white))

        when (v.id) {
            R.id.txt_no_tip -> {
                tipAmount = 0
            }

            R.id.txt_tips_1 -> {
                tipAmount = 1
            }

            R.id.txt_tips_2 -> {
                tipAmount = 2
            }

            R.id.txt_tips_5 -> {
                tipAmount = 5
            }

            R.id.txt_tips_other -> {

                    val modal = SheetGiveTip()
                    modal.show(supportFragmentManager, SheetGiveTip.TAG)

            }
        }
    }
}