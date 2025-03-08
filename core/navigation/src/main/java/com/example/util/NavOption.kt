package com.example.util

import androidx.navigation.NavOptions
import com.example.navigation.R

object NavOption {
    val rightAnim = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()

    val upAnim = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_up_in)
        .setExitAnim(R.anim.slide_up_out)
        .setPopEnterAnim(R.anim.slide_down_in)
        .setPopExitAnim(R.anim.slide_down_out)
        .build()

}