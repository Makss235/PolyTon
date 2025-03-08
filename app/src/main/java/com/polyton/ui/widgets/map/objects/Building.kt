package com.polyton.ui.widgets.map.objects

import android.content.Context
import android.graphics.RectF
import android.view.MotionEvent
import com.polyton.data.map.BuildingData
import com.polyton.ui.widgets.map.draw.BuildingDrawSystem

class Building(private val context: Context,
               private val buildingData: BuildingData) {

    private val floors = ArrayList<Floor>()
    private lateinit var currentFloor : Floor

    private var isActivate = false
    private val buildingRect = RectF(Float.MAX_VALUE, Float.MAX_VALUE, 0f, 0f)
    private val buildingDrawSystem = BuildingDrawSystem(this)

    init {
        initFloors()
    }

    fun getFloors() : ArrayList<Floor> {
        return floors
    }

    fun getCurrentFloor() : Floor {
        return currentFloor
    }

    fun setCurrentFloor(currentFloor: Floor) {
        this.currentFloor = currentFloor
    }

    fun getBuildingRect() : RectF {
        return buildingRect
    }

    fun getBuildingDraw() : BuildingDrawSystem {
        return buildingDrawSystem
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {
        currentFloor.onTouchEvent(event, x, y)
    }

    private fun initFloors() {
        for (floorData in buildingData.floors) {
            val floor = Floor(context, buildingData, floorData)
            floors.add(floor)

            buildingRect.left = buildingRect.left.coerceAtMost(floor.getFloorRect().left)
            buildingRect.top = buildingRect.top.coerceAtMost(floor.getFloorRect().top)
            buildingRect.right = buildingRect.right.coerceAtLeast(floor.getFloorRect().right)
            buildingRect.bottom = buildingRect.bottom.coerceAtLeast(floor.getFloorRect().bottom)
        }
        currentFloor = floors[0]
    }
}