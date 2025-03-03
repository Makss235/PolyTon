package com.polyton.ui.widgets.map.objects

import android.content.Context
import com.polyton.data.map.BuildingData
import com.polyton.ui.widgets.map.draw.BuildingDrawSystem

class Building(private val context: Context,
               private val buildingData: BuildingData) {

    private val floors = ArrayList<Floor>()
    private lateinit var currentFloor : Floor

    private var isActivate = false
    private val buildingDrawSystem = BuildingDrawSystem(this)

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