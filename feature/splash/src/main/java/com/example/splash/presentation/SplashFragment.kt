package com.example.splash.presentation

import com.example.ui.base.BaseFragment
import com.example.common.extension.Extension.isInternetAvailable
import com.example.splash.databinding.FragmentSplashBinding


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate){


    override fun initUi() {
       checkInternetConnection()
    }

    override fun initObservers() {

    }

    fun checkInternetConnection(){
        if(!requireContext().isInternetAvailable()){
            //showCustomDialog(message =getString(com.example.ui.R.string.internet_connection_error_title) ,
            //okCallBack =::checkInternetConnection)
        }
    }

}