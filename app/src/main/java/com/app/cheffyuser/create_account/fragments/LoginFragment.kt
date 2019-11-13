package com.app.cheffyuser.create_account.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.cheffyuser.BottomNavActivity
import com.app.cheffyuser.R
import com.app.cheffyuser.create_account.model.Data
import com.app.cheffyuser.create_account.model.LoginData
import com.app.cheffyuser.create_account.model.UserModel
import com.app.cheffyuser.home.BaseFragment
import com.app.cheffyuser.networking.Constant
import com.app.cheffyuser.networking.remote.ApiClient
import com.app.cheffyuser.networking.remote.ApiInterface
import com.app.cheffyuser.utils.createSnack
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment() {

    private var apiInterface: ApiInterface? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    private val userModel: List<UserModel>? = null
    private val userData: List<Data>? = null


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

        var alertDialog = LottieAlertDialog.Builder(activity, DialogTypes.TYPE_LOADING)
            .setTitle("Setting you up...")
            .setDescription("Please Wait")
            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()

        val builder = LottieAlertDialog.Builder(activity, DialogTypes.TYPE_ERROR)


        val loginData = LoginData(email, password)
        val call = apiInterface!!.userLogin(loginData)

        call.enqueue(object : Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {


                if (response.body() != null && response.isSuccessful) {

                    alertDialog.dismiss()
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

                    Timber.d("Success1 ${response.body()!!.token}")
                    val token = response.body()!!.token
                    val id = response.body()!!.data.id
                    val name = response.body()!!.data.name
                    val email = response.body()!!.data.email

                    val lat = response.body()!!.data.locationLat
                    val lon = response.body()!!.data.locationLon
                    val country_code = response.body()!!.data.countryCode
                    val phone_no = response.body()!!.data.phoneNo

                    val image_path = response.body()!!.data.imagePath

                    Toast.makeText(activity, "$id $name $email $country_code", Toast.LENGTH_SHORT)
                        .show()


                    //TODO: Remove all shared preferences code here !

                    val sp = activity!!.getSharedPreferences(
                        Constant.SHARED_PREF_NAME,
                        Context.MODE_PRIVATE
                    )

                    //Creating editor to store values to shared preferences
                    val editor = sp.edit()
                    //Adding values to editor
                    editor.putString(Constant.SP_TOKEN, token)
                    editor.putInt(Constant.SP_ID, id!!)
                    editor.putString(Constant.SP_NAME, name)
                    editor.putString(Constant.SP_EMAIL, email)
                    editor.putString(Constant.SP_COUNTRY_CODE, country_code)
                    editor.putString(Constant.SP_PHONE_NO, phone_no)
                    editor.putString(Constant.SP_IMAGE_PATH, image_path)
                    editor.putString(Constant.SP_LOCATION_LAT, lat)
                    editor.putString(Constant.SP_LOCATION_LON, lon)


                    //Saving values to editor
                    editor.apply()


                    val intent = Intent(activity, BottomNavActivity::class.java)
                    startActivity(intent)

                } else {

                    Toast.makeText(context, "User or password is invalid!", Toast.LENGTH_SHORT)
                        .show()

                    //TODO: Remove this repeated block
                    alertDialog.changeDialog(builder)
                    Handler().postDelayed({ alertDialog.dismiss() }, 3000)
                    alertDialog.setCancelable(true)
                }
            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {

                alertDialog.changeDialog(builder)
                Handler().postDelayed({ alertDialog.dismiss() }, 3000)
                Timber.d(t.localizedMessage)
                alertDialog.setCancelable(true)

            }
        })


        //        call.enqueue(new Callback<LoginData>() {
        //            @Override
        //            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
        //                progressDialog.dismiss();
        //
        //                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
        //            }
        //
        //            @Override
        //            public void onFailure(Call<LoginData> call, Throwable t) {
        //                progressDialog.dismiss();
        //                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
        //            }
        //        });


    }

}// Required empty public constructor
