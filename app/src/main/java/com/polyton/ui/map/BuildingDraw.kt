package com.polyton.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.text.method.Touch
import android.view.MotionEvent
import com.polyton.data.BuildingData


class BuildingDraw(private val context: Context) : Drawable() {
    private var flag: Boolean = false
    lateinit var bData: BuildingData

    val floorDraws: ArrayList<FloorDraw> = ArrayList()
    lateinit var currentFloor: FloorDraw
    var rectF: RectF = RectF(Float.MAX_VALUE, Float.MAX_VALUE, 0f, 0f)

    private val paint : Paint = Paint()

    fun initBuilding(buildingData: BuildingData) {
        bData = buildingData

        for (fData in bData.floors!!) {
            val floorDraw = FloorDraw(context)
            floorDraw.initFloor(fData, bData)
            floorDraws.add(floorDraw)
            rectF.left = rectF.left.coerceAtMost(floorDraw.rectF.left)
            rectF.top = rectF.top.coerceAtMost(floorDraw.rectF.top)
            rectF.right = rectF.right.coerceAtLeast(floorDraw.rectF.right)
            rectF.bottom = rectF.bottom.coerceAtLeast(floorDraw.rectF.bottom)
        }
        currentFloor = floorDraws[1]

        paint.style = Paint.Style.FILL
        paint.color = Color.RED
    }

    override fun draw(p0: Canvas) {
        currentFloor.draw(p0)
        if (flag) {
            p0.drawRect(rectF, paint)
            flag = false
        }

//        p0.drawCircle(100f, 100f, 5f, paint)
//        p0.drawCircle(200f, 200f, 5f, paint)
//        p0.drawCircle(300f, 300f, 5f, paint)
//        p0.drawCircle(400f, 400f, 5f, paint)
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {
        currentFloor.onTouchEvent(event, x, y)
    }

    override fun setAlpha(p0: Int) {}

    override fun setColorFilter(p0: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE;
    }
}