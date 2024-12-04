package com.polyton.ui.map

import android.graphics.Canvas
import android.view.SurfaceHolder

class MapThread(
    private val surfaceHolder: SurfaceHolder,
    surfaceView: MapView) : Thread() {

    private val mapView: MapView = surfaceView
    private var isRun = false

    fun setRunning(b: Boolean) {
        isRun = b
    }

    override fun run() {
        // super.run();
        while (isRun) {
            var c: Canvas? = null
            try {
                c = surfaceHolder.lockCanvas(null)
                synchronized(surfaceHolder) {
                    mapView.myDraw(c)
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c)
                }
            }
        }
    }
}