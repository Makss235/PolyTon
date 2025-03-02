package com.polyton.ui.map2.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.polyton.ui.map2.Floor
import com.polyton.ui.map2.draw.base.DrawSystem

class FloorDrawSystem(private val floor : Floor) : DrawSystem() {

    private val paint = Paint()

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.BLUE
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(200f, 200f, 20f, paint)
    }
}