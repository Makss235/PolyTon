package com.polyton.ui.map.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import com.polyton.data.parsers.Vertex

class MapPoint(private val context: Context, var vertex : Vertex) : Drawable() {

    private val paint : Paint = Paint()
    private val textPaint : Paint = Paint()

    val r : RectF
    private var title : String = ""

    var isActivate = false

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.GREEN

        textPaint.color = Color.BLACK
        textPaint.textSize = 25f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        r = RectF(vertex.x.toFloat() - 32, vertex.y.toFloat() - 18,
            vertex.x.toFloat() + 32, vertex.y.toFloat() + 18)

        var t = vertex.id
        if (t.length > 2) {
            if (t[0] == '_' && t[t.length - 1] == '_') {
                t = t.substring(1 until t.length)
                val parts = t.split('_')
                if (parts[0] == "A" && parts.size > 1)
                    title = parts[1]
            }
        }
    }

    override fun draw(p0: Canvas) {

//        p0.drawText(vertex.x.toString() + " " + vertex.y, vertex.x.toFloat() - 10,
//            vertex.y.toFloat() - 10, paint)
        p0.drawRoundRect(r, 7f, 7f, paint)
        p0.drawText(title, vertex.x.toFloat(), vertex.y.toFloat() + 9f, textPaint)
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {
        if (event.action == MotionEvent.ACTION_UP) {
            if (isActivate) {
                deactivate()
            }
            else {
                activate()
            }
        }
    }

    private fun activate() {
        paint.color = Color.YELLOW
        isActivate = true
    }

    private fun deactivate() {
        paint.color = Color.GREEN
        isActivate = false
    }

    fun answer(r: Boolean) {
        if (r) {
            paint.color = Color.RED
        }
        else {
            paint.color = Color.GREEN
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