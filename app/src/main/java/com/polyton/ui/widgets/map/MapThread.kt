package com.polyton.ui.widgets.map

import android.graphics.Canvas
import android.view.SurfaceHolder

class MapThread(
    private val surfaceHolder: SurfaceHolder,
    surfaceView: MapWidget
) : Thread() {

    private val mapWidget: MapWidget = surfaceView
    private var isRun = false

    fun setRunning(boolValue: Boolean) {
        isRun = boolValue
    }

    override fun run() {
        // super.run();
        while (isRun) {
            var canvas: Canvas? = null
            try {
                canvas = surfaceHolder.lockCanvas(null)
                synchronized(surfaceHolder) {
                    mapWidget.getMapDrawSystem().draw(canvas)
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}