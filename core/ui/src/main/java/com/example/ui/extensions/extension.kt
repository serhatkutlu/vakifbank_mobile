package com.example.ui.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

object extension {

    fun ImageView.loadImageWithGlide(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }

    fun View.visible(){
        this.visibility=View.VISIBLE
    }

    fun View.gone(){
        this.visibility=View.GONE
    }

    fun View.invisible(){
        this.visibility=View.INVISIBLE
    }

}