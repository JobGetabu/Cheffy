package com.app.cheffyuser.create_account.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.Data
import com.app.cheffyuser.create_account.model.SignupRequest
import com.app.cheffyuser.create_account.model.UserModel
import com.app.cheffyuser.create_account.model.UserType
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.BaseFragment
import com.app.cheffyuser.networking.remote.ApiClient
import com.app.cheffyuser.networking.remote.ApiInterface
import com.app.cheffyuser.networking.remote.Status
import com.app.cheffyuser.utils.createSnack


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment() {

    private var apiInterface: ApiInterface? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    private val userModel: List<UserModel>? = null
    private val userData: List<Data>? = null

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(AuthViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        etEmail = view.findViewById(R.id.et_email)
        etPassword = view.findViewById(R.id.et_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)

        // btnLogin.setShadowLayer(24,100,100, Color.RED);

        btnLogin.setOnClickListener { login() }
        return view
    }


    private fun login() {

        val email = etEmail?.text.toString().trim { it <= ' ' }
        val password = etPassword?.text.toString().trim { it <= ' ' }

        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

        if (!isConnected) {
            activity?.let {
                createSnack(
                    it, getString(R.string.you_not_connected), getString(R.string.retry),
                    View.OnClickListener { login() })
            }
            return
        }

        val dialog = showDialogue("Logging in", "Please wait ...")

        //test coroutine call

        val signupRequest = SignupRequest("name","$email", UserType.USER, "$password")

        vm.createAccount(signupRequest).observe(this, Observer {
            when(it.status){
                Status.ERROR -> {
                    errorDialogue("Error","User or password is invalid!",dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog)
                    //dialog?.dismiss()
                    //TODO: Go to next screen
                    //TODO: Update
                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })
    }

}
