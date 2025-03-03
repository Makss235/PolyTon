package com.polyton.ui.widgets.map.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import com.polyton.ui.widgets.map.draw.base.DrawSystem
import com.polyton.ui.widgets.map.objects.Point

class PointDrawSystem(private val context: Context, private val point : Point) : DrawSystem() {

    val backPaint : Paint = Paint()
    private val textPaint : Paint = Paint()

    init {
        backPaint.style = Paint.Style.FILL
        backPaint.color = Color.GREEN

        textPaint.color = Color.BLACK
        textPaint.textSize = 25f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(point.getPointRect(), 7f, 7f, backPaint)
        canvas.drawText(point.getTitle(), point.getVertex().x.toFloat(),
            point.getVertex().y.toFloat() + 9f, textPaint)
    }
}