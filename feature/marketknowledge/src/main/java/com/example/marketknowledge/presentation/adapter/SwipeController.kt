package com.example.marketknowledge.presentation.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.DisplayMetrics

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.marketknowledge.R

class SwipeController(private val context: Context,private val recyclerView: RecyclerView) : ItemTouchHelper.Callback() {

    private var screenWidth: Int = 0
    private var buttonWidth = 0
    private var totalButtonWidth = 0
    private val paint = Paint()

    private var previousViewHolder: RecyclerView.ViewHolder? = null
    init {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels

        totalButtonWidth = screenWidth/3
        buttonWidth = totalButtonWidth/2


    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val viewType = viewHolder.itemViewType
        return if (viewType ==MarketKnowledgeAdapter.ItemType.HEADER.ordinal ) {
            makeMovementFlags(0, 0)
        } else {
            makeMovementFlags(0, ItemTouchHelper.LEFT)
        }
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        //        }
        previousViewHolder?.let { prevHolder ->
            recyclerView.adapter?.notifyItemChanged(prevHolder.adapterPosition) // Önceki öğeyi sıfırla
        }
        previousViewHolder = viewHolder
    }


        override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val limitedDx = dX/3
        val buttonLeft = itemView.right.toFloat() + limitedDx
        paint.color = context.getColor(com.example.ui.R.color.red)
        val deleteButton = RectF(
            buttonLeft + buttonWidth,
            itemView.top.toFloat(),
            buttonLeft + totalButtonWidth,
            itemView.bottom.toFloat()
        )
        c.drawRect(deleteButton, paint)

        paint.color = Color.WHITE
        paint.textSize = 40f
        paint.textAlign = Paint.Align.CENTER
        c.drawText(context.getString(R.string.market_header_sell), deleteButton.centerX(), deleteButton.centerY() + 15, paint)


        paint.color = context.getColor(com.example.ui.R.color.green)
        val editButton = RectF(
            buttonLeft,
            itemView.top.toFloat(),
            buttonLeft + buttonWidth,
            itemView.bottom.toFloat()
        )
        c.drawRect(editButton, paint)

        paint.color = Color.WHITE
        c.drawText(context.getString(R.string.market_header_buy), editButton.centerX(), editButton.centerY() + 15, paint)

        super.onChildDraw(c, recyclerView, viewHolder, limitedDx, dY, actionState, isCurrentlyActive)
    }


    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return .9f
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return defaultValue * 15f
    }







}
