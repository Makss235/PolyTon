package com.polyton.ui.map2

import android.content.Context
import com.polyton.data.map.BuildingData
import com.polyton.data.map.FloorData
import com.polyton.ui.map2.draw.FloorDraw

class Floor(private val context: Context,
            private val buildingData: BuildingData,
            private val floorData: FloorData) {
    private val floorDraw: FloorDraw = FloorDraw(context)

    init {

    }
}