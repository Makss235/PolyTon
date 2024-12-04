package com.polyton.ui.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import com.caverock.androidsvg.SVG
import com.polyton.data.BuildingData
import com.polyton.data.FloorData

class FloorDraw(private val context: Context) : Drawable() {

    private lateinit var fData : FloorData
    private lateinit var bData : BuildingData

    private lateinit var svg: SVG
    private lateinit var rectF: RectF

    fun initFloor(floorData : FloorData, buildingData : BuildingData) {
        fData = floorData
        bData = buildingData

        svg = SVG.getFromAsset(context.assets,
            "buildings/b_${bData.id}/f_${fData.number}.svg")
        rectF = RectF(bData.x, bData.y,
            bData.x + bData.width,
            bData.y + bData.height)
    }

    override fun draw(p0: Canvas) {
        svg.renderToCanvas(p0, rectF)
    }

    override fun setAlpha(p0: Int) {
    }

    override fun setColorFilter(p0: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE;
    }
}