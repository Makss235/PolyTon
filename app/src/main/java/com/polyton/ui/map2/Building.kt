package com.polyton.ui.map2

import android.content.Context
import android.graphics.Canvas
import com.polyton.data.map.BuildingData
import com.polyton.ui.map2.draw.BuildingDrawSystem

class Building(private val context: Context,
               private val buildingData: BuildingData) {

    private val buildingDrawSystem = BuildingDrawSystem(this)
    private var isActivate = false

    private val floors = ArrayList<Floor>()
    private lateinit var currentFloor : Floor

    init {
        initFloors()
    }

    fun getBuildingDraw() : BuildingDrawSystem {
        return buildingDrawSystem
    }

    fun getCurrentFloor() : Floor {
        return currentFloor
    }

    private fun initFloors() {
        for (floorData in buildingData.floors) {
            val floor = Floor(context, buildingData, floorData)
            floors.add(floor)
        }
        currentFloor = floors[0]
    }
}