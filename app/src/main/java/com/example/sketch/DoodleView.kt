package com.example.sketch

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DoodleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var templatePaint: Paint = Paint().apply{
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 20f
    }

    private var currentStrokePaint: Paint = Paint(templatePaint)
    private var currentPath: Path = Path()

    private var bitmap: Bitmap? = null
    private var bitmapCanvas: Canvas? = null

    private val backgroundColor = Color.WHITE

    private val strokeCaps = arrayOf(Paint.Cap.ROUND, Paint.Cap.SQUARE, Paint.Cap.BUTT)
    private var currentStrokeIndex = 0
    var currentStrokeCap = Paint.Cap.ROUND

    var currentSize = 20f

    var currentColor = Color.BLACK
    var currentSketchPage = 0
    var currentW = 0
    var currentH = 0

    val pages: MutableMap<Int, Bitmap?> = mutableMapOf()

    init{
    }

    fun changeColor(color: String){
        val newColor = color
        templatePaint.color = Color.parseColor(newColor)
        currentColor = Color.parseColor(newColor)
    }

    fun changeStrokeCap(){
        currentStrokeIndex = (currentStrokeIndex + 1) % strokeCaps.size
        val newCap = strokeCaps[currentStrokeIndex]
        currentStrokeCap = newCap
        templatePaint.strokeCap = newCap
    }

    fun setNewBrushSize(newSize: Float) {
        templatePaint.strokeWidth = newSize
        currentSize = newSize
    }

    fun saveAndGet(page: Int){
        if(page < 0){
            return
        }
        if (pages[page] == null){
            pages[currentSketchPage] = bitmap
            val newBitmap = Bitmap.createBitmap(currentW, currentH, Bitmap.Config.ARGB_8888)
            val newCanvas = Canvas(newBitmap)
            newCanvas.drawColor(backgroundColor)

            bitmap = newBitmap
            bitmapCanvas = newCanvas

            pages[page] = newBitmap
        } else {
            pages[currentSketchPage] = bitmap

            var newBitmap: Bitmap? = pages[page]

            bitmapCanvas = Canvas(newBitmap!!)
            bitmap = newBitmap
        }
        currentSketchPage = page
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int){
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            if (bitmap != null) {
                bitmap?.recycle()
            }

            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmapCanvas = Canvas(bitmap!!)
            bitmapCanvas?.drawColor(backgroundColor)

            currentW = w
            currentH = h
        }
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap!!, 0f, 0f, null)
        canvas.drawPath(currentPath, currentStrokePaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentStrokePaint = Paint(templatePaint)
                currentPath.reset()
                currentPath.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                currentPath.lineTo(x, y)
                bitmapCanvas?.drawPath(currentPath, currentStrokePaint)
                currentPath.reset()
            }
        }
        invalidate()
        return true
    }
}