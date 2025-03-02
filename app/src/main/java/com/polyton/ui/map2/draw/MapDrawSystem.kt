package com.polyton.ui.map2.draw

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import com.polyton.ui.map2.MapWidget
import com.polyton.ui.map2.draw.base.DrawSystem

class MapDrawSystem(private val mapWidget: MapWidget) : DrawSystem() {

    private lateinit var tempBitmap : Bitmap
    private lateinit var tempCanvas : Canvas

    private val buildingDraws = ArrayList<BuildingDraw>()

    private val paint = Paint()

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.GREEN
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.drawColor(Color.WHITE)

        tempCanvas.save()
        tempCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        tempCanvas.concat(mapWidget.getMapGestureSystem().getTransformationMatrix())
        tempCanvas.concat(mapWidget.getMapGestureSystem().getScaleMatrix())

        tempCanvas.drawCircle(200f, 200f, 20f, paint)

//        for (buildingDraw in buildingDraws) {
//            buildingDraw.draw(tempCanvas)
//        }

        tempCanvas.restore()
        canvas.drawBitmap(tempBitmap, 0f, 0f, null)

//        canvas.scale(3f, 3f)
//
//        canvas.drawText(xTouch.toString(), 20f, 20f, paint)
//        canvas.drawText(yTouch.toString(), 20f, 40f, paint)
//        canvas.drawText(x.toString(), 20f, 60f, paint)
//        canvas.drawText(y.toString(), 20f, 80f, paint)
//        canvas.drawText(((xTouch - x)/scaleFactor).toString(), 20f, 100f, paint)
//        canvas.drawText(((yTouch - y)/scaleFactor).toString(), 20f, 120f, paint)
//        canvas.drawText((scaleFactor).toString(), 20f, 140f, paint)
//
        canvas.restore()
    }

    fun onLayout(width: Int, height: Int) {
        tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        tempCanvas = Canvas(tempBitmap)
    }
}