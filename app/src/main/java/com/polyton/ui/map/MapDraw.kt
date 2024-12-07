package com.polyton.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Scroller
import com.polyton.data.DataFromParsers

class MapDraw @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {
    private var thread: MapThread? = null

    private val buildingDraws: ArrayList<BuildingDraw> = ArrayList<BuildingDraw>()
    lateinit var currentBuilding : BuildingDraw

    private var scaleFactor = 1f

    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())
    private val gestureDetector = GestureDetector(context, GestureListener())
    private val scroller: Scroller = Scroller(context)

    private lateinit var tempBitmap : Bitmap
    private lateinit var tempCanvas : Canvas
    private val transformationMatrix = Matrix()
    private val scaleMatrix = Matrix()

    private val paint : Paint = Paint()

    // Полные координаты
    private var x = 0f
    private var y = 0f

    // Только трансформация
    private var xT = 0f
    private var yT = 0f

    // Координаты при скейлинге
    private var xScale = 0f
    private var yScale = 0f

    // Координаты при скейлинге (пред скейлинг)
    private var xScalePrev = 0f
    private var yScalePrev = 0f

    private var xTouch = 0f
    private var yTouch = 0f

    init {
        holder.addCallback(this)
        isFocusable = true

        for (bData in DataFromParsers.getBuildings()) {
            val buildingDraw = BuildingDraw(context)
            buildingDraw.initBuilding(bData)
            buildingDraws.add(buildingDraw)
        }
        currentBuilding = buildingDraws[0]

        paint.style = Paint.Style.FILL
        paint.color = Color.GREEN
    }

    fun myDraw(canvas: Canvas) {
        canvas.save()
        canvas.drawColor(Color.WHITE)

        tempCanvas.save()
        tempCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        tempCanvas.drawCircle(0f, 0f, 20f, paint)
        tempCanvas.concat(transformationMatrix)
        tempCanvas.concat(scaleMatrix)

        for (b in buildingDraws)
            b.draw(tempCanvas)

        tempCanvas.restore()

        canvas.drawBitmap(tempBitmap, 0f, 0f, null)

        canvas.scale(3f, 3f)

        canvas.drawText(xTouch.toString(), 20f, 20f, paint)
        canvas.drawText(yTouch.toString(), 20f, 40f, paint)
        canvas.drawText(x.toString(), 20f, 60f, paint)
        canvas.drawText(y.toString(), 20f, 80f, paint)
        canvas.drawText(((xTouch - x)/scaleFactor).toString(), 20f, 100f, paint)
        canvas.drawText(((yTouch - y)/scaleFactor).toString(), 20f, 120f, paint)
        canvas.drawText((scaleFactor).toString(), 20f, 140f, paint)

        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        xTouch = event.x
        yTouch = event.y

        val xxx = (xTouch - x)/scaleFactor
        val yyy = (yTouch - y)/scaleFactor

        for (b in buildingDraws) {
            if (b.rectF.contains(xxx, yyy)) {
                b.onTouchEvent(event, xxx, yyy)
                break
            }
        }

        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        tempCanvas = Canvas(tempBitmap)
    }

    override fun surfaceChanged(
        arg0: SurfaceHolder, arg1: Int, arg2: Int,
        arg3: Int
    ) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = MapThread(getHolder(), this)
        thread!!.setRunning(true)
        thread!!.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
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

//    override fun computeScroll() {
//        if (scroller.computeScrollOffset()) {
//            val oldX = scrollX
//            val oldY = scrollY
//            val x = scroller.currX
//            val y = scroller.currY
//            scrollTo(x, y)
//
//            if (oldX != scrollX || oldY != scrollY) {
//                onScrollChanged(scrollX, scrollY, oldX, oldY)
//            }
//            invalidate()
//        }
//    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            scaleFactor *= detector.scaleFactor
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f))
            scaleMatrix.setScale(scaleFactor, scaleFactor,
                width / 2f, height / 2f)

            val v = FloatArray(9)
            scaleMatrix.getValues(v)
            val xS = v[Matrix.MTRANS_X]
            val yS = v[Matrix.MTRANS_Y]

            x = x - xScalePrev + xS
            y = y - yScalePrev + yS
            xScalePrev = xS
            yScalePrev = yS

            return true
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent,
            distanceX: Float, distanceY: Float): Boolean {
            x -= distanceX
            y -= distanceY
            xT -= distanceX
            yT -= distanceY
            transformationMatrix.reset()
            transformationMatrix.postTranslate(xT, yT)
            return true
        }

//        override fun onFling(e1: MotionEvent?, e2: MotionEvent,
//            velocityX: Float, velocityY: Float): Boolean {
//            scroller.fling(x.toInt(), y.toInt(),
//                -velocityX.toInt(), -velocityY.toInt(),
//                -10000, 10000, -10000, 10000)
//
//            return true
//        }
    }
}