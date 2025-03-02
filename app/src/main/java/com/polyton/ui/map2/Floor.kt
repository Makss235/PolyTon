package com.polyton.ui.map2

import android.content.Context
import com.polyton.data.map.BuildingData
import com.polyton.data.map.FloorData
import com.polyton.ui.map2.draw.FloorDrawSystem

class Floor(private val context: Context,
            private val buildingData: BuildingData,
            private val floorData: FloorData) {

    private val floorDrawSystem: FloorDrawSystem = FloorDrawSystem(this)

    fun getFloorDrawSystem() : FloorDrawSystem {
        return floorDrawSystem
    }
}