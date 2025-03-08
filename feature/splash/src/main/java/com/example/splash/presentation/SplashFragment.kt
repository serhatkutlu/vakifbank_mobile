package com.example.splash.presentation

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ui.base.BaseFragment
import com.example.common.extension.Extension.isInternetAvailable
import com.example.splash.R
import com.example.splash.databinding.FragmentSplashBinding
import com.example.util.NavOption
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate){


    override fun initUi() {
       checkInternetConnection()
    }

    override fun initObservers() {

    }

    fun checkInternetConnection(){
        if(!requireContext().isInternetAvailable()){
            showCustomDialog(message =getString(com.example.ui.R.string.internet_connection_error_title) ,
            okCallBack =::checkInternetConnection)
        }else
        {
            lifecycleScope.launch {
                delay(2000)
                findNavController().navigate(com.example.navigation.R.id.loginFragment,null, navOptions =NavOption.navOptionPopUp(
                    com.example.navigation.R.id.splashFragment))
            }


        }
    }

}