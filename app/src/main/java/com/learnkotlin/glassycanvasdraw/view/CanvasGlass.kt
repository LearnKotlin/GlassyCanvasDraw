package com.learnkotlin.glassycanvasdraw.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.learnkotlin.glassycanvasdraw.R


class CanvasGlass(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    //region Variable

    private var overlayBitmap: Bitmap? = null
    private val paint = Paint()
    private val boxSize = resources.getDimensionPixelSize(R.dimen.boxsize)

    private var sourceRect = Rect(0, 0, boxSize, boxSize)
    private var dstRect = Rect(0, 0, 100, 100)

    //endregion

    //region ImageView

    init {
        setWillNotDraw(false)
        paint.color = Color.BLUE
        paint.strokeWidth = resources.getDimensionPixelSize(R.dimen.stroke).toFloat();
        paint.style = Paint.Style.STROKE
        setOnTouchListener { v, event -> handleTouch(event) }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        overlayBitmap?.let {
            canvas?.drawBitmap(it, sourceRect, dstRect, paint)
            canvas?.drawRect(dstRect, paint)
        }

    }

    //endregion

    //region Public Interface

    fun setOverlayResource(resource: Int){

        overlayBitmap = BitmapFactory.decodeResource(resources, resource)
        drawOverlay(null)

    }

    //endregion

    //region Private

    private fun handleTouch(event: MotionEvent): Boolean {
        drawOverlay(event)
        return true
    }

    private fun drawOverlay(event: MotionEvent?){

        overlayBitmap?.let {

            val bitmapToViewRatio: Float = it.height /  height.toFloat()
            val actualSize: Float = boxSize / bitmapToViewRatio

            var viewX = (event?.x?.toInt() ?: width/2) - actualSize.toInt()/2
            var viewY = (event?.y?.toInt() ?: height/2) - actualSize.toInt()/2

            viewX = ensureRange(viewX, 0, width - actualSize.toInt())
            viewY = ensureRange(viewY, 0, height - actualSize.toInt())

            val bitmapX = (viewX * bitmapToViewRatio).toInt()
            val bitmapY = (viewY * bitmapToViewRatio).toInt()

            sourceRect = Rect(bitmapX.toInt(), bitmapY.toInt(), (bitmapX + boxSize).toInt(), (bitmapY + boxSize).toInt())
            dstRect = Rect(viewX, viewY, viewX + actualSize.toInt(), viewY + actualSize.toInt())
            invalidate()

        }
    }

    private fun ensureRange(value: Int, min: Int, max: Int): Int {
        return Math.min(Math.max(value, min), max)
    }

    //endregion
}