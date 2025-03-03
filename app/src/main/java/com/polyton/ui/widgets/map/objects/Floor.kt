package com.polyton.ui.widgets.map.objects

import android.content.Context
import com.polyton.data.map.BuildingData
import com.polyton.data.map.FloorData
import com.polyton.data.parsers.Graph
import com.polyton.data.parsers.XMLFloorParser
import com.polyton.ui.widgets.map.draw.FloorDrawSystem

class Floor(private val context: Context,
            private val buildingData: BuildingData,
            private val floorData: FloorData) {

    private val basePath = "buildings/b_${buildingData.id}/"
    private val pathToFloorSvgFile = "${basePath}f_${floorData.number}.svg"
    private val pathToGraphSvgFile = "${basePath}g_${floorData.number}.svg"

    private var xRatio : Double = 0.0
    private var yRatio : Double = 0.0

    private var graph : Graph
    private var points : MutableList<Point> = mutableListOf()

    private val floorDrawSystem: FloorDrawSystem = FloorDrawSystem(context, this)

    init {

        xRatio = (buildingData.width)/(3251.43 + 17.04)
        yRatio = (buildingData.height)/(3251.43 + 5*17.04)

        graph = XMLFloorParser(context, pathToGraphSvgFile).graph

        for (v in graph.vertices) {
            v.x = v.x * xRatio + buildingData.x
            v.y = v.y * yRatio + buildingData.y + 13 * yRatio
            if (v.id != "") {
                val p = Point(context, v)
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
}