package com.example.CustomBottomNav

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.Menu

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

import com.bumptech.glide.Glide
import com.example.ui.R
import com.example.ui.databinding.CustomBottomViewRowBinding

class CustomBottomNavigation(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {


    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    init {
        orientation = HORIZONTAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        gravity = CENTER

    }


    fun setMenu(menu: Menu) {
        removeAllViews()
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            if (menuItem.title.isNullOrBlank()) {

                val imageView = ImageView(context)
                val layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
                imageView.setPadding(30, 30, 30, 30)
                Glide.with(this)
                    .load(R.drawable.vibi)
                    .into(imageView)


                runnable = Runnable {
                    if (context is AppCompatActivity && !(context as AppCompatActivity).isDestroyed && !(context as AppCompatActivity).isFinishing) {
                        Glide.with(context)
                            .load(R.drawable.vibi)
                            .into(imageView)
                    }
                }
                runnable?.run()
                runnable?.let { handler.postDelayed(it, 2000) }

                imageView.layoutParams = layoutParams
                addView(imageView)

            } else {
                val row =
                    CustomBottomViewRowBinding.inflate(LayoutInflater.from(context), this, false)
                        .apply {
                            ivIcon.setImageDrawable(menuItem.icon)
                            tvTitle.text = menuItem.title
                            tvTitle.setTextAppearance(R.style.bottom_nav_text_appearance)
                            this@apply.root.setOnClickListener {
                                onItemSelectedListener?.invoke(menuItem.itemId)
                            }
                        }
                addView(row.root)
            }


        }
    }

    private var onItemSelectedListener: ((Int) -> Unit)? = null


    fun setOnItemSelectedListener(listener: (Int) -> Unit) {
        onItemSelectedListener = listener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        runnable?.let { handler.removeCallbacks(it) }
    }

}
