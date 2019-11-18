package com.app.cheffyuser.home.fragments


import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 *
 * Check implementation in  [PlateFragment]
 */
class KitchenFragment : PlateFragment() {


 /*   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_kitchen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initTablayout()
    }


    private fun initTablayout() {
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ReviewDetailbarAdapter(childFragmentManager)
        adapter.addFragments(PlateDetailsFragment())
        adapter.addFragments(PlateReviewFragment())

        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 2

        tab_layout.setupWithViewPager(viewpager)
    }*/

}
