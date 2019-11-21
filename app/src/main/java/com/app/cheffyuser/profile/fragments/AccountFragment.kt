package com.app.cheffyuser.profile.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.cheffyuser.R
import com.app.cheffyuser.home.fragments.BaseFragment
import com.app.cheffyuser.home.viewmodel.HomeViewModel
import com.app.cheffyuser.profile.activities.EditProfileActivity
import com.app.cheffyuser.profile.adapter.ProfilebarAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_user_main.*


/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : BaseFragment() {

    private val vm: HomeViewModel by lazy {
        ViewModelProviders.of(getActivity()!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uiStuff()
        initTablayout()
    }

    private fun uiStuff() {


        tv_user_name.text = "Iron Man"
        tv_user_address.text = "Birmingham"

        //Loading profile
        Glide.with(activity!!)
            .load("")
            .placeholder(R.drawable.avatar_placeholder)
            .error(R.drawable.avatar_placeholder)
            .into(user_image)


        img_profile_edit.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initTablayout() {
        profile_tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ProfilebarAdapter(childFragmentManager)
        adapter.addFragments(UserFavoriteFragment())
        adapter.addFragments(AccountsSettingFragment())

        profile_view_pager.adapter = adapter
        profile_view_pager.offscreenPageLimit = 2

        profile_tab_layout.setupWithViewPager(profile_view_pager)

    }

}
