package com.polyton.ui.map2

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.polyton.ui.map.MapThread
import com.polyton.ui.map2.draw.MapDrawSystem

class MapWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) :
    SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var thread: MapThread? = null
    private val mapDrawSystem = MapDrawSystem(this)
    private val mapGestureSystem = MapGestureSystem(this)

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    fun getMapDrawSystem() : MapDrawSystem {
        return mapDrawSystem
    }

    fun getMapGestureSystem() : MapGestureSystem {
        return mapGestureSystem
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return getMapGestureSystem().onTouchEvent(event)
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mapDrawSystem.onLayout(width, height)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        thread = MapThread(getHolder(), this)
        thread?.setRunning(true)
        thread?.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        thread?.setRunning(false)
        while (retry) {
            try {
                thread?.join()
                retry = false
            } catch (e: InterruptedException) {
            }
        }
    }
}