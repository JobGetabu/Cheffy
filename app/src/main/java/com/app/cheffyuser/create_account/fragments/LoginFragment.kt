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
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.activities.ForgotPasswordActivity
import com.app.cheffyuser.create_account.model.SignupRequest
import com.app.cheffyuser.create_account.model.UserType
import com.app.cheffyuser.create_account.social.FacebookManager
import com.app.cheffyuser.create_account.social.GoogleManager
import com.app.cheffyuser.create_account.viewmodel.AuthViewModel
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.networking.remote.Status
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
        const val EMAIL_EXTRA: String = "EMAIL_EXTRA"
        private const val RC_SIGN_IN = 1
    }

    private var facebookManager: FacebookManager? = null
    private var signInOptions: GoogleSignInOptions? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var alertDialog: LottieAlertDialog? = null


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
            //TODO: Uncomment
            //facebookManager = FacebookManager(it.applicationContext)
        }

    }

    private fun uiStaff() {
        btnLogin.setOnClickListener {
            login()
        }

        googleLogin.setOnClickListener {
            googleLogin()
        }

        forgotPassword.setOnClickListener {
            startActivity(ForgotPasswordActivity.newIntent(activity!!))
        }
    }


    private fun login() {

        val email = etEmail?.editText.toString().trim()
        val password = etPassword?.editText.toString().trim()

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

        val signupRequest = SignupRequest("name", "$email", UserType.USER, "$password")

        vm.createAccount(signupRequest).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "User or password is invalid!", dialog!!)
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

                    GoogleManager(activity!!, account, object : GoogleManager.GoogleLoginListener {
                        override fun onSuccess() {
                            alertDialog?.dismiss()

                            // At this point user date has been saved to
                            // local shared preferences

                            //TODO: goTo => HomeActivity

                        }

                        override fun onError(message: String) {
                            errorDialogue(alertDialog= alertDialog)
                        }
                    })
                } else {
                    errorDialogue(alertDialog= alertDialog)

                    Timber.d(task1.exception)
                    Timber.e("onActivityResult:failed " + task1.exception!!.message)
                }
            }
        } else {
            facebookManager!!.onActivityResult(requestCode, resultCode, data!!)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

}
