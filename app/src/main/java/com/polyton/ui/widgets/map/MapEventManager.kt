package com.polyton.ui.widgets.map

import com.polyton.data.map.BuildingData
import com.polyton.data.map.FloorData
import com.polyton.ui.map.draw.MapPoint
import com.polyton.ui.widgets.map.objects.Point
import kotlinx.event.event

object MapEventManager {
    val pointClicked = event<PointEventArgs>()
}

data class PointEventArgs(val state: Point.PointState,
                          val point: Point,
                          val buildingData: BuildingData,
                          val floorData: FloorData)