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
import com.app.cheffyuser.create_account.activities.VerifyActivity
import com.app.cheffyuser.create_account.model.AccessToken
import com.app.cheffyuser.create_account.model.SignupRequest
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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : BaseFragment() {

    companion object {
        const val CHECKOUT_IN_PROGRESS: String = "CHECKOUT_IN_PROGRESS"
        private const val RC_SIGN_IN = 1
    }

    private val vm: AuthViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(AuthViewModel::class.java)
    }

    private var facebookManager: FacebookManager? = null
    private var signInOptions: GoogleSignInOptions? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var alertDialog: LottieAlertDialog? = null
    private var fbLoginInProgress = false
    private val tm = CheffyApp.instance!!.tokenManager
    private var checkoutInProgress = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uiStaff()

        configureGoogleSignIn()

        activity?.let {
            facebookManager = FacebookManager(vm, activity!!, true)
        }

        checkoutInProgress = activity!!.intent.getBooleanExtra(CHECKOUT_IN_PROGRESS, false)
    }

    private fun uiStaff() {
        btn_next.setOnClickListener {
            signup()
        }

        googleLogin.setOnClickListener {
            googleLogin()
        }

        fbLogin.setOnClickListener {
            facebookLogin()
        }
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

        fbLoginInProgress = true
        alertDialog = showDialogue(title = "Facebook Signing in")


        facebookManager!!.login(activity!!, object : FacebookManager.FacebookLoginListener {
            override fun onSuccess() {
                Toast.makeText(activity!!, "Welcome " + " !", Toast.LENGTH_SHORT)
                    .show()

                fbLoginInProgress = false
                facebookManager!!.clearSession()
                alertDialog?.dismiss()

                startActivity(BottomNavActivity.newIntent(context!!).apply {
                    Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
                })

                activity?.finish()

            }

            override fun onError(message: String) {

                fbLoginInProgress = false
                errorDialogue(alertDialog = alertDialog, descriptions = message)
                Timber.d(message)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("onActivityResult: $requestCode")
        facebookManager!!.onActivityResult(requestCode, resultCode, data!!)

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

                    GoogleManager(
                        vm,
                        activity!!,
                        true,
                        account,
                        object : GoogleManager.GoogleLoginListener {
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

                    Timber.d(task1.exception)
                    Timber.e("onActivityResult:failed " + task1.exception!!.message)
                }
            }
        } else {
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onDestroy() {
        super.onDestroy()
        facebookManager!!.onDestroy()
    }

    private fun signup() {
        val email = etEmail.editText?.text.toString()

        if (email.isEmpty()) {
            etEmail.isErrorEnabled = true
            etEmail.error = "valid email required"
            return
        }

        if (!isConnected) {
            activity?.let {
                createSnack(
                    it, getString(R.string.you_not_connected), getString(R.string.retry),
                    View.OnClickListener { signup() })
            }
            return
        }

        val dialog = showDialogue("Processing email", "Please wait ...")

        val sReq = SignupRequest(email)

        vm.createAccount(sReq).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errorDialogue("Error", "${it.message}", dialog!!)
                }
                Status.SUCCESS -> {
                    successDialogue(alertDialog = dialog)

                    val res = it.data

                    if (res != null) {
                        //save in prefs
                        if (res.token != null)
                            tokenManager.saveToken(AccessToken(res.token))
                        tokenManager.email = email

                        val intent = Intent(activity, VerifyActivity::class.java)
                        startActivity(intent)
                    }
                }
                Status.LOADING -> {
                    //still loading data
                }
            }
        })
    }

}