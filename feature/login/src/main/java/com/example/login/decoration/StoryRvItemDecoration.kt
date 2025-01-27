package com.example.login.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StoryRvItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position= parent.getChildAdapterPosition(view)
            val itemCount = state.itemCount
            outRect.left= space
            if (position==itemCount-1){
                outRect.right=space
            }
        }
    }
