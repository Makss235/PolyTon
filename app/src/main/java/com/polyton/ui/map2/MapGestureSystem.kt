package com.polyton.ui.map2

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector

class MapGestureSystem(private val mapWidget: MapWidget) {
    private val scaleDetector = ScaleGestureDetector(mapWidget.context, ScaleListener())
    private val gestureDetector = GestureDetector(mapWidget.context, GestureListener())

    private val transformationMatrix = Matrix()
    private val scaleMatrix = Matrix()

    private var scaleFactor = 1f

    // Полные координаты
    var x = 0f
    var y = 0f

    // Только трансформация
    var xT = 0f
    var yT = 0f

    // Координаты при скейлинге
    var xScale = 0f
    var yScale = 0f

    // Координаты при скейлинге (пред скейлинг)
    var xScalePrev = 0f
    var yScalePrev = 0f

    var xTouch = 0f
    var yTouch = 0f

    fun getTransformationMatrix() : Matrix {
        return transformationMatrix
    }

    fun getScaleMatrix() : Matrix {
        return scaleMatrix
    }

//    fun getFullX() : Float {
//        return x;
//    }
//    fun setFullX(x: Float) {
//        this.x = x;
//    }
//
//    fun getFullY() : Float {
//        return y;
//    }
//    fun setFullY(y: Float) {
//        this.y = y;
//    }
//
//    fun getTransformationX() : Float {
//        return xT;
//    }
//    fun getTransformationX() : Float {
//        return xT;
//    }
//
//    fun getTransformationY() : Float {
//        return yT;
//    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            scaleFactor *= detector.scaleFactor
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f))
            scaleMatrix.setScale(scaleFactor, scaleFactor,
                mapWidget.width / 2f, mapWidget.height / 2f)

            val v = FloatArray(9)
            scaleMatrix.getValues(v)
            val xS = v[Matrix.MTRANS_X]
            val yS = v[Matrix.MTRANS_Y]

            x = x - xScalePrev + xS
            y = y - yScalePrev + yS
            xScalePrev = xS
            yScalePrev = yS

            return true
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent,
                              distanceX: Float, distanceY: Float): Boolean {
            x -= distanceX
            y -= distanceY
            xT -= distanceX
            yT -= distanceY
            transformationMatrix.reset()
            transformationMatrix.postTranslate(xT, yT)
            return true
        }

//        override fun onFling(e1: MotionEvent?, e2: MotionEvent,
//            velocityX: Float, velocityY: Float): Boolean {
//            scroller.fling(x.toInt(), y.toInt(),
//                -velocityX.toInt(), -velocityY.toInt(),
//                -10000, 10000, -10000, 10000)
//
//            return true
//        }
    }
}