package com.polyton.ui.widgets.map.objects

import android.content.Context
import android.graphics.RectF
import android.view.MotionEvent
import com.polyton.data.map.BuildingData
import com.polyton.data.map.FloorData
import com.polyton.data.parsers.Graph
import com.polyton.data.parsers.XMLFloorParser
import com.polyton.ui.map.draw.PointEventArgs
import com.polyton.ui.widgets.map.draw.FloorDrawSystem

class Floor(private val context: Context,
            private val buildingData: BuildingData,
            private val floorData: FloorData) {

    private val basePath = "buildings/b_${buildingData.id}/"
    private val pathToFloorSvgFile = "${basePath}f_${floorData.number}.svg"
    private val pathToGraphSvgFile = "${basePath}g_${floorData.number}.svg"

    private var xRatio : Double = (buildingData.width)/(3251.43 + 17.04)
    private var yRatio : Double = (buildingData.height)/(3251.43 + 5*17.04)

    private var graph : Graph = XMLFloorParser(context, pathToGraphSvgFile).graph
    private var points : MutableList<Point> = mutableListOf()

    private val floorDrawSystem: FloorDrawSystem = FloorDrawSystem(context, this)

    init {

        for (vertex in graph.vertices) {
            vertex.x = vertex.x * xRatio + buildingData.x
            vertex.y = vertex.y * yRatio + buildingData.y + 13 * yRatio
            if (vertex.id != "") {
                val p = Point(context, vertex)
                points.add(p)
            }
        }
    }

    fun getBuildingData() : BuildingData {
        return buildingData
    }

    fun getFloorData() : FloorData {
        return floorData
    }

    fun getPathToFloorSvgFile() : String {
        return pathToFloorSvgFile
    }

    fun getPoints() : MutableList<Point> {
        return points
    }

    fun getFloorDrawSystem() : FloorDrawSystem {
        return floorDrawSystem
    }

    fun getFloorRect() : RectF {
        return floorDrawSystem.getFloorRect()
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {
        if (event.action == MotionEvent.ACTION_UP) {
            for (point in points) {
                if (point.getPointRect().contains(x, y)) {
                    point.onTouchEvent(event, x, y)
                    break
                }
            }
        }
    }
}