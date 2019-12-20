package com.app.cheffyuser.create_account.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.CheffyApp
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.activities.ForgotPasswordActivity
import com.app.cheffyuser.create_account.model.LoginRequest
import com.app.cheffyuser.create_account.social.FacebookManager
import com.app.cheffyuser.create_account.social.GoogleManager
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.activities.BottomNavActivity
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.networking.Status
import com.app.cheffyuser.utils.createSnack
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment() {

    companion object {
        const val CHECKOUT_IN_PROGRESS: String = "CHECKOUT_IN_PROGRESS"
        private const val RC_SIGN_IN = 1
    }

    private var facebookManager: FacebookManager? = null
    private var signInOptions: GoogleSignInOptions? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var alertDialog: LottieAlertDialog? = null
    private val tm = CheffyApp.instance!!.tokenManager
    private var checkoutInProgress = false

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(AuthViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uiStaff()

        configureGoogleSignIn()

        activity?.let {
            facebookManager = FacebookManager(vm, activity!!)
        }

        checkoutInProgress = activity!!.intent.getBooleanExtra(CHECKOUT_IN_PROGRESS, false)
    }

    private fun uiStaff() {
        btnLogin.setOnClickListener {
            login()
        }

        googleLogin.setOnClickListener {
            googleLogin()
        }

        fbLogin.setOnClickListener {
            facebookLogin()
        }

        forgotPassword.setOnClickListener {
            startActivity(ForgotPasswordActivity.newIntent(activity!!))
        }
    }


    private fun validate(): Boolean {
        var valid = true

        val email = etEmail?.editText?.text.toString().trim()
        val password = etPassword?.editText?.text.toString().trim()

        if (email.isEmpty() or !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.isErrorEnabled = true
            etEmail.error = "Enter valid email address"
            valid = false
        } else {
            etEmail.error = null
            valid = true
        }

        if (password.length < 6) {
            etPassword.isErrorEnabled = true
            etPassword?.error = "at least 6 characters"
            valid = false
        } else {
            etPassword.error = null
            valid = true
        }

        Timber.d("validate => $valid ")

        return valid
    }

    private fun login() {

        val email = etEmail?.editText?.text.toString().trim()
        val password = etPassword?.editText?.text.toString().trim()

        if (!validate()) {
            return
        }

        if (!isConnected) {
            activity?.let {
                createSnack(
                    it, getString(R.string.you_not_connected), getString(R.string.retry),
                    View.OnClickListener { login() })
            }
            return
        }

        val dialog = showDialogue("Logging in", "Please wait ...")

        val loginReq = LoginRequest(email, password)

        vm.loginUser(loginReq).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it.message}", dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog)

                    val res = it.data

                    //save in prefs
                    tm.user = res
                    tm.setIsLoggedIn()

                    if (checkoutInProgress) {

                    } else {

                        startActivity(BottomNavActivity.newIntent(context!!).apply {
                            Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }

                    activity!!.finish()
                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })
    }

    private fun configureGoogleSignIn() {
        signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity!!, signInOptions!!)
    }

    private fun googleLogin() {

        if (!isConnected) {
            activity?.let {
                createSnack(
                    it, getString(R.string.you_not_connected), getString(R.string.retry),
                    View.OnClickListener { googleLogin() })
            }
            return
        }

        val signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        alertDialog = showDialogue(title = "Google Signing in")
    }

    private fun facebookLogin() {
        if (!isConnected) {
            activity?.let {
                createSnack(
                    it, getString(R.string.you_not_connected), getString(R.string.retry),
                    View.OnClickListener { facebookLogin() })
            }
            return
        }

        alertDialog = showDialogue(title = "Facebook Signing in")

        facebookManager!!.login(activity!!, object : FacebookManager.FacebookLoginListener {
            override fun onSuccess() {
                Toast.makeText(activity!!, "Welcome " + " !", Toast.LENGTH_SHORT)
                    .show()

                facebookManager!!.clearSession()
                alertDialog?.dismiss()

                startActivity(BottomNavActivity.newIntent(context!!).apply {
                    Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
                })

                activity?.finish()

            }

            override fun onError(message: String) {

                errorDialogue(alertDialog = alertDialog, descriptions = message)
                Timber.d(message)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("onActivityResult: $requestCode")
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.addOnCompleteListener { task1 ->
                //handle successfull sign in
                if (task1.isSuccessful) {
                    val account = task1.result

                    Toast.makeText(
                        activity,
                        "Welcome " + account!!.displayName!! + " !",
                        Toast.LENGTH_SHORT
                    ).show()

                    GoogleManager(vm, activity!!,false, account, object : GoogleManager.GoogleLoginListener {
                        override fun onSuccess() {
                            alertDialog?.dismiss()

                            // At this point user date has been saved to
                            // local shared preferences
                            //goTo => HomeActivity

                            startActivity(BottomNavActivity.newIntent(context!!).apply {
                                Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
                            })

                            activity?.finish()

                        }

                        override fun onError(message: String) {
                            errorDialogue(alertDialog = alertDialog, descriptions = message)
                        }
                    })
                } else {
                    errorDialogue(alertDialog = alertDialog)

                    Timber.d(task1.exception.toString())
                    Timber.e("onActivityResult:failed " + task1.exception!!.message)
                }
            }
        } else {
            facebookManager!!.onActivityResult(requestCode, resultCode, data!!)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()

        //facebookManager?.onDestroy()
    }

}
