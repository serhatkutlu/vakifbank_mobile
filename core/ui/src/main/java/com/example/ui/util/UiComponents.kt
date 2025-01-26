package com.example.ui.util

interface UiComponents {


    fun initUi()


    fun showCustomDialog(
        message: String,
        isInfoMessage: Boolean,
        cancelCallBack: (() -> Unit)? = null,
        okCallBack: (() -> Unit)? = null,
    )

    fun initObservers()

}