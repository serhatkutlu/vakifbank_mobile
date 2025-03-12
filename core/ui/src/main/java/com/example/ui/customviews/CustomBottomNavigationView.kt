    package com.example.ui.customviews

    import android.annotation.SuppressLint
    import android.content.Context
    import android.graphics.Canvas
    import android.graphics.Color
    import android.graphics.Paint
    import android.graphics.Path
    import android.util.AttributeSet
    import android.view.Gravity

    import android.widget.FrameLayout

    import com.example.CustomBottomNav.CustomBottomNavigation

    import com.example.ui.R

    class CustomBottomNavigationView@JvmOverloads constructor (context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : FrameLayout(context, attrs,defStyleAttr) {
         var customBottomNavigation: CustomBottomNavigation? = null

        private var lineColor: Int= Color.BLACK
        private var bgColor:Int= Color.WHITE

        init {
            removeAllViews()
            setWillNotDraw(false)
            attrs?.let {
                val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomBottomNavigationView,defStyleAttr,0)
                lineColor = typedArray.getColor(R.styleable.CustomBottomNavigationView_lineColor,Color.BLACK)
                bgColor = typedArray.getColor(R.styleable.CustomBottomNavigationView_bgColor, Color.WHITE)
                typedArray.recycle()
            }
            customBottomNavigation=CustomBottomNavigation(context,attrs)
            val params=LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
            params.gravity=Gravity.BOTTOM
            customBottomNavigation?.layoutParams=params


            addView(customBottomNavigation)



        }

        private val paint = Paint().apply {
            color = bgColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        private val paint2 = Paint().apply {
            color = lineColor
            style = Paint.Style.STROKE
            strokeWidth = 2f
            isAntiAlias = true
        }


        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)



            val width = width.toFloat()
            val height = height.toFloat()
            var curveHeight = 70f


            val path = createCurvePath(width, curveHeight)
            val path2 = createCurvePath(width, curveHeight)


            path.lineTo(width, height)
            path.lineTo(0f, height)
            path.close()


            canvas.drawPath(path, paint)
            canvas.drawPath(path2, paint2)




        }
        private fun createCurvePath(width: Float, curveHeight: Float = 100f, margin: Float = 5f): Path {
            return Path().apply {
                moveTo(0f, curveHeight)

                lineTo(width / 2 - 180, curveHeight)


                cubicTo(
                    width / 2 - 100,
                    curveHeight,
                    width / 2 - 90,
                    0f + margin,
                    width / 2,
                    0f + margin
                )
                cubicTo(
                    width / 2 + 90,
                    0f + margin,
                    width / 2 + 100,
                    curveHeight,
                    width / 2 + 180,
                    curveHeight
                )

                lineTo(width, curveHeight)
            }
        }


    }
