package com.polyton.ui.widgets.map.draw

import android.graphics.Canvas
import com.polyton.ui.widgets.map.objects.Building
import com.polyton.ui.widgets.map.draw.base.DrawSystem

class BuildingDrawSystem(private val building: Building) : DrawSystem() {

    override fun draw(canvas: Canvas) {
        building.getCurrentFloor().getFloorDrawSystem().draw(canvas)
    }
}