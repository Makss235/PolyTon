package com.polyton.ui.widgets.map.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.view.MotionEvent
import com.caverock.androidsvg.SVG
import com.polyton.ui.widgets.map.objects.Floor
import com.polyton.ui.widgets.map.draw.base.DrawSystem

class FloorDrawSystem(private val context: Context, private val floor : Floor) : DrawSystem() {

    private var svg: SVG
    private var floorRect: RectF

//    private val paint2 : Paint = Paint()

    init {

//        paint2.style = Paint.Style.STROKE
//        paint2.color = Color.BLUE
//        paint2.strokeWidth = 3f
//        paint2.isDither = true
//        paint2.style = Paint.Style.STROKE
//        paint2.strokeJoin = Paint.Join.ROUND
//        paint2.strokeCap = Paint.Cap.ROUND
//        paint2.pathEffect = CornerPathEffect(10F)
//        paint2.isAntiAlias = true

        val buildingData = floor.getBuildingData()
        val floorData = floor.getFloorData()

        svg = SVG.getFromAsset(context.assets, floor.getPathToFloorSvgFile())
        floorRect = RectF(buildingData.x, buildingData.y,
            buildingData.x + buildingData.width,
            buildingData.y + (svg.documentViewBox.bottom - svg.documentViewBox.top) /
                    ((svg.documentViewBox.right - svg.documentViewBox.left) / buildingData.width))
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {

    }

    override fun draw(canvas: Canvas) {
        svg.renderToCanvas(canvas, floorRect)

        for (p in floor.getPoints()) {
            p.getPointDrawSystem().draw(canvas)
        }
    }
}