package com.polyton.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import com.polyton.data.parsers.Vertex

class MapPoint(private val context: Context, var vertex : Vertex) : Drawable() {

    private val paint : Paint = Paint()
    var isActivate = false

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.GREEN
    }

    override fun draw(p0: Canvas) {
        p0.drawCircle(vertex.x.toFloat(), vertex.y.toFloat(), 5f, paint)
//        p0.drawText(vertex.x.toString() + " " + vertex.y, vertex.x.toFloat() - 10,
//            vertex.y.toFloat() - 10, paint)
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {
        if (event.action == MotionEvent.ACTION_UP)
            if (isActivate) {
                paint.color = Color.GREEN
                isActivate = false
            }
            else {
                paint.color = Color.RED
                isActivate = true
            }
    }

    override fun setAlpha(p0: Int) {
    }

    override fun setColorFilter(p0: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE;
    }
}