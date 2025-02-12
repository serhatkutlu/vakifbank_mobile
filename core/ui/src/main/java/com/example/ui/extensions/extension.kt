package com.example.ui.extensions

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

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
    fun Fragment.launchAndRepeatWithViewLifecycle(block: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                block()
            }
        }
    }

}