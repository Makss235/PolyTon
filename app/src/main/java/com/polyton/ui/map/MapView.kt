package com.polyton.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Scroller
import com.caverock.androidsvg.SVG
import com.polyton.data.MapThread

class MapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var thread: MapThread? = null

    private var svg: SVG
    private var rectF: RectF

    private var scaleFactor = 1f
    private val matrix = Matrix()

    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())
    private val gestureDetector = GestureDetector(context, GestureListener())
    private val scroller: Scroller = Scroller(context)

    init {
        holder.addCallback(this)
        isFocusable = true

        svg = SVG.getFromAsset(context.assets,
            "buildings/b_11/f_1.svg")
        rectF = RectF(100f, 100f,
            1000f, 1000f)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f
        paint.color = Color.RED
    }

    fun myDraw(canvas: Canvas) {
        canvas.save()
//        canvas.concat(matrix)

        canvas.drawRGB(255, 255, 255)
        canvas.drawCircle(100f, 100f, 20f, paint)
        svg.renderToCanvas(canvas, rectF)

        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
//        rectF.left = event.x
//        Log.v("1111", "${event.x} ${event.y}")
//        rectF.top = event.y
//        rectF.right = event.x + 900
//        rectF.bottom = event.y + 900
        return true
    }

    override fun surfaceChanged(
        arg0: SurfaceHolder, arg1: Int, arg2: Int,
        arg3: Int
    ) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = MapThread(getHolder(), this)
        thread!!.setRunning(true)
        thread!!.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread?.setRunning(false)
        while (retry) {
            try {
                thread?.join()
                retry = false
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            val oldX = scrollX
            val oldY = scrollY
            val x = scroller.currX
            val y = scroller.currY
            scrollTo(x, y)

            if (oldX != scrollX || oldY != scrollY) {
                onScrollChanged(scrollX, scrollY, oldX, oldY)
            }
            invalidate()
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            scaleFactor *= detector.scaleFactor
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f))
            matrix.setScale(scaleFactor, scaleFactor, width / 2 + scrollX.toFloat(),
                height / 2 + scrollY.toFloat())
            return true
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            rectF.left -= distanceX
            rectF.top -= distanceY
            rectF.right -= distanceX
            rectF.bottom -= distanceY
            return true
        }

//        override fun onFling(
//            e1: MotionEvent?,
//            e2: MotionEvent,
//            velocityX: Float,
//            velocityY: Float
//        ): Boolean {
//            scroller.fling(
//                scrollX,
//                scrollY,
//                -velocityX.toInt(),
//                -velocityY.toInt(),
//                -10000,
//                10000,
//                -10000,
//                10000
//            )
//
//            return true
//        }
    }
}