package com.example.login.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.login.presentation.fragments.CommercialFragment
import com.example.login.presentation.fragments.IndividualFragment

class PagerAdapter(fragment:Fragment):FragmentStateAdapter(fragment) {


    private val fragmentHeights = listOf(650, 850)


    override fun getItemCount()=2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> IndividualFragment()
            1->CommercialFragment()
            else -> IndividualFragment()

        }

    }
    fun getFragmentHeight(position: Int): Int {
        return fragmentHeights[position]
    }

}