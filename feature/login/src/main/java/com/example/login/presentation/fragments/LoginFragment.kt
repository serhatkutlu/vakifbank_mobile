package com.example.login.presentation.fragments

import BaseFragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.login.databinding.FragmentLoginBinding
import com.example.login.decoration.StoryRvItemDecoration
import com.example.login.presentation.adapter.PagerAdapter
import com.example.login.presentation.adapter.StoryRvAdapter
import com.google.android.material.tabs.TabLayoutMediator


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val storyRvAdapter by lazy {
            StoryRvAdapter(listOf("test1","test2","test3","test4","test5","test6","test7","test8","test9")){

        }

    }


    private val pagerAdapter by lazy {
        PagerAdapter(this)
    }
    override fun initUi() {
        initStoryRv()
        initViewPager()
    }

    private fun initViewPager() {
        binding.includeLoginMainView.apply {
            vpPager.adapter=pagerAdapter
            TabLayoutMediator(tlTab,vpPager){tab,position->
                when(position){
                    0->tab.text="İndividual"
                    1->tab.text="Commercial"
                }

                }.attach()
            vpPager.registerOnPageChangeCallback(onPageChangeCallBack(vpPager))
        }

    }

    private fun initStoryRv() {
        binding.includeLoginMainView.rvStory.apply {
            adapter=storyRvAdapter
            setHasFixedSize(true)
            layoutManager=
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(StoryRvItemDecoration(50))
        }
    }

    override fun initObservers() {
    }


    private fun onPageChangeCallBack(viewPager: ViewPager2) =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val newHeight = (viewPager.adapter as PagerAdapter).getFragmentHeight(position)
                val layoutParams = viewPager.layoutParams
                layoutParams.height = newHeight
                viewPager.layoutParams = layoutParams
            }
        }
}