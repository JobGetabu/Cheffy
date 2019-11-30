package com.app.cheffyuser.create_account.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.activities.CreateAccountActivity
import com.app.cheffyuser.home.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_need_to_login.*

/**
 * A simple [Fragment] subclass.
 */
class NeedToLoginFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_need_to_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_login.setOnClickListener {
            val intent = Intent(
                context,
                CreateAccountActivity::class.java
            )
            startActivity(intent)
        }


        btn_signup.setOnClickListener {
            val intent = Intent(
                context,
                CreateAccountActivity::class.java
            )
            startActivity(intent)
        }
    }


}
