package com.polyton.ui.widgets.map.objects

import android.content.Context
import android.graphics.Color
import android.graphics.RectF
import android.view.MotionEvent
import com.polyton.data.map.BuildingData
import com.polyton.data.map.FloorData
import com.polyton.data.parsers.Vertex
import com.polyton.ui.widgets.map.MapEventManager
import com.polyton.ui.widgets.map.PointEventArgs
import com.polyton.ui.widgets.map.draw.PointDrawSystem
import kotlinx.event.event

class Point(private val context : Context,
            private val buildingData : BuildingData,
            private val floorData : FloorData,
            private val vertex : Vertex,
            val owner : Floor) {

    enum class PointState {
        ACTIVE, SELECTED, NORMAL
    }

    val stateChanged = event<PointState>()

    private val pointRect : RectF = RectF(
        vertex.x.toFloat() - 32, vertex.y.toFloat() - 18,
        vertex.x.toFloat() + 32, vertex.y.toFloat() + 18)
    private var title : String = ""
    private var isClickOnPoint = false
    var state : PointState = PointState.NORMAL
        set(value) {
            field = value
            stateChanged(state)
        }

    private val pointDrawSystem = PointDrawSystem(context, this)

    init {
        var vId = vertex.id
        if (vId.length > 2) {
            if (vId[0] == '_' && vId[vId.length - 1] == '_') {
                vId = vId.substring(1 until vId.length)
                val parts = vId.split('_')
                if (parts[0] == "A" && parts.size > 1)
                    title = parts[1]
            }
        }
    }

    fun getVertex() : Vertex {
        return vertex
    }

    fun getPointRect() : RectF {
        return pointRect
    }

    fun getTitle() : String {
        return title
    }

    fun getPointDrawSystem() : PointDrawSystem {
        return pointDrawSystem
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {
        if (event.action == MotionEvent.ACTION_DOWN)
            isClickOnPoint = true
        if (event.action == MotionEvent.ACTION_MOVE)
            isClickOnPoint = false
        if (event.action == MotionEvent.ACTION_UP && isClickOnPoint) {

            state = when(state) {
                PointState.ACTIVE -> PointState.SELECTED
                PointState.SELECTED -> PointState.NORMAL
                PointState.NORMAL -> PointState.SELECTED
            }

            MapEventManager.pointClicked(PointEventArgs(
                state, this, buildingData, floorData))

            isClickOnPoint = false
        }
    }

    fun answer(boolean: Boolean) {
        state = if (boolean)
            PointState.ACTIVE
        else PointState.NORMAL
    }
}