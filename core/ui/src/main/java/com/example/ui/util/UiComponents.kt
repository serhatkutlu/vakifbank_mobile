package com.example.ui.util

interface UiComponents {


    fun initUi()


    fun showCustomDialog(
        message: String,
        oktext:String="Okey",
        okCallBack: (() -> Unit)? = null,
    )

    fun initObservers()

}