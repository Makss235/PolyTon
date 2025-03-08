package com.polyton.ui.widgets.map.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.RectF
import com.caverock.androidsvg.SVG
import com.polyton.data.parsers.Vertex
import com.polyton.ui.widgets.map.objects.Floor
import com.polyton.ui.widgets.map.draw.base.DrawSystem

class FloorDrawSystem(private val context: Context, private val floor : Floor) : DrawSystem() {

    private var svg: SVG
    private var floorRect: RectF

    private var pointsAlgo : MutableList<Vertex> = mutableListOf()

    private val pathPaint : Paint = Paint()

    init {
        pathPaint.style = Paint.Style.STROKE
        pathPaint.color = Color.BLUE
        pathPaint.strokeWidth = 3f
        pathPaint.isDither = true
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeJoin = Paint.Join.ROUND
        pathPaint.strokeCap = Paint.Cap.ROUND
        pathPaint.pathEffect = CornerPathEffect(10F)
        pathPaint.isAntiAlias = true

        val buildingData = floor.getBuildingData()
        val floorData = floor.getFloorData()

        floor.pathBuilt += { pathBuild(it) }

        svg = SVG.getFromAsset(context.assets, floor.getPathToFloorSvgFile())
        floorRect = RectF(buildingData.x, buildingData.y,
            buildingData.x + buildingData.width,
            buildingData.y + (svg.documentViewBox.bottom - svg.documentViewBox.top) /
                    ((svg.documentViewBox.right - svg.documentViewBox.left) / buildingData.width))
    }

    fun getFloorRect() : RectF {
        return floorRect
    }

    private fun pathBuild(pointsAlgo : MutableList<Vertex>) {
        this.pointsAlgo = pointsAlgo
    }

    override fun draw(canvas: Canvas) {
        svg.renderToCanvas(canvas, floorRect)

        if (pointsAlgo.size > 0) {
            for (i in 0 until pointsAlgo.size - 1) {
                canvas.drawLine(pointsAlgo[i].x.toFloat(),
                    pointsAlgo[i].y.toFloat(),
                    pointsAlgo[i + 1].x.toFloat(),
                    pointsAlgo[i + 1].y.toFloat(), pathPaint)
            }
        }

        for (point in floor.getPoints()) {
            point.getPointDrawSystem().draw(canvas)
        }
    }
}