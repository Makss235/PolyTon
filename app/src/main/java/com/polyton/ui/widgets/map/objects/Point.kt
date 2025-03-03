package com.polyton.ui.widgets.map.objects

import android.content.Context
import android.graphics.RectF
import com.polyton.data.parsers.Vertex
import com.polyton.ui.widgets.map.draw.PointDrawSystem

class Point(private val context: Context, private val vertex : Vertex) {

    private val pointRect : RectF = RectF(
        vertex.x.toFloat() - 32, vertex.y.toFloat() - 18,
        vertex.x.toFloat() + 32, vertex.y.toFloat() + 18)
    private var title : String = ""

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
}