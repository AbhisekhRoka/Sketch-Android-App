package com.example.sketch

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.sqrt

class ColorWheelView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var colorWheelBitmap: Bitmap? = null
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    var colorSelectedListener: ((Int) -> Unit)? = null

    init {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isInEditMode) {
            return
        }

        colorWheelBitmap?.let {
            canvas.drawBitmap(it, centerX - radius, centerY - radius, null)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = min(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (isInEditMode) return

        val size = min(w, h)
        centerX = size / 2f
        centerY = size / 2f
        radius = size / 2f

        colorWheelBitmap?.recycle()

        generateColorWheel()
    }

    private fun generateColorWheel() {
        val diameter = (radius * 2).toInt()

        if (diameter <= 0) {
            colorWheelBitmap = null
            return
        }

        colorWheelBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(colorWheelBitmap!!)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val colors = FloatArray(3)

        for (y in 0 until diameter) {
            for (x in 0 until diameter) {
                val dx = x - radius
                val dy = y - radius
                val distance = sqrt(dx * dx + dy * dy)

                if (distance <= radius) {
                    val angle = atan2(dy, dx).toDouble()
                    val hue = (Math.toDegrees(angle).toFloat() + 180)
                    val saturation = distance / radius

                    colors[0] = hue
                    colors[1] = saturation
                    colors[2] = 1.0f

                    paint.color = Color.HSVToColor(colors)
                    canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
                }
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x - centerX
            val y = event.y - centerY
            val distanceFromCenter = sqrt(x * x + y * y)

            if (distanceFromCenter <= radius) {
                val angle = atan2(y.toDouble(), x.toDouble())
                val hue = (Math.toDegrees(angle).toFloat() + 180)
                val saturation = distanceFromCenter / radius
                val selectedColor = Color.HSVToColor(floatArrayOf(hue, saturation, 1.0f))

                colorSelectedListener?.invoke(selectedColor)
            }
        }
        return true
    }
}