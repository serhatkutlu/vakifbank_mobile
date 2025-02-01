package com.example.login.presentation.fragments

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.ui.base.BaseFragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.common.resource.ResourceUi
import com.example.login.R
import com.example.login.databinding.FragmentLoginBinding
import com.example.login.decoration.StoryRvItemDecoration
import com.example.login.presentation.adapter.PagerAdapter
import com.example.login.presentation.adapter.StoryRvAdapter
import com.example.login.presentation.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewmodel by viewModels<LoginViewModel>()


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

    override fun initObservers() {

    }

    private fun initViewPager() {
        binding.includeLoginMainView.apply {
            vpPager.adapter=pagerAdapter
            TabLayoutMediator(tlTab,vpPager){tab,position->
                when(position){
                    0->tab.text=getText(R.string.individual_tab_name)
                    1->tab.text=getText(R.string.commercial_tab_name)
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

    override suspend fun onFragmentStarted() {
        viewmodel.loginState.collect{
            when(it.storyData){
               is  ResourceUi.Success ->{
                   it.storyData.data?.get(0)?.title?.let { it1 -> Log.d("serhat", it1) }
               }
                else->{
                }
            }
        }
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