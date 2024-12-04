package com.polyton.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import com.polyton.data.BuildingData


class BuildingDraw(private val context: Context) : Drawable() {
    private lateinit var bData: BuildingData

    val floorDraws: ArrayList<FloorDraw> = ArrayList()
    lateinit var currentFloor: FloorDraw

    private val paint : Paint = Paint()

    fun initBuilding(buildingData: BuildingData) {
        bData = buildingData

        for (fData in bData.floors!!) {
            val floorDraw = FloorDraw(context)
            floorDraw.initFloor(fData, bData)
            floorDraws.add(floorDraw)
        }
        currentFloor = floorDraws[0]

        paint.style = Paint.Style.FILL
        paint.color = Color.GREEN
    }

    override fun draw(p0: Canvas) {
        currentFloor.draw(p0)
    }

    override fun setAlpha(p0: Int) {}

    override fun setColorFilter(p0: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE;
    }
}