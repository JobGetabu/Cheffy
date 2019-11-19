package com.app.cheffyuser.home.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.cheffyuser.R
import com.app.cheffyuser.home.adapter.IngredienceAdapter
import com.app.cheffyuser.home.model.PlatesResponse
import com.app.cheffyuser.utils.Constants
import com.app.cheffyuser.utils.loadUrl
import kotlinx.android.synthetic.main.activity_receipt_details.*
import kotlinx.android.synthetic.main.single_ingredient_layout.*

class ReceiptDetailsActivity : BaseActivity() {

    private var platesResponse: PlatesResponse? = null

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ReceiptDetailsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_details)

        platesResponse = intent.getParcelableExtra(Constants.PLATES_RESPONSE_EXTRA)

        setIngredienceList()

        receiptimage.loadUrl(platesResponse?.receiptImages?.get(0)?.url)
    }

    private fun setIngredienceList() {
        ingredient_list.setHasFixedSize(true)

        val ingredients = platesResponse?.ingredients

        val adapter = IngredienceAdapter(true, this, ingredients?.toMutableList())

        ingredient_list.adapter = adapter
    }
}
