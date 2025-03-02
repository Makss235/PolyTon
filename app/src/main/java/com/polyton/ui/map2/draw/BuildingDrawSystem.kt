package com.polyton.ui.map2.draw

import android.graphics.Canvas
import com.polyton.ui.map2.Building
import com.polyton.ui.map2.draw.base.DrawSystem

class BuildingDrawSystem(private val building: Building) : DrawSystem() {

    override fun draw(canvas: Canvas) {
        building.getCurrentFloor().getFloorDrawSystem().draw(canvas)
    }
}