package com.polyton.ui.map.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import com.caverock.androidsvg.SVG
import com.polyton.data.map.BuildingData
import com.polyton.data.DijkstraAlgorithm
import com.polyton.data.map.FloorData
import com.polyton.data.parsers.Graph
import com.polyton.data.parsers.Vertex
import com.polyton.data.parsers.XMLFloorParser
import kotlinx.event.event

class FloorDraw(private val context: Context) : Drawable() {

    private lateinit var fData : FloorData
    private lateinit var bData : BuildingData

    private lateinit var graph : Graph
    private lateinit var algorithm : DijkstraAlgorithm
    private var points : MutableList<MapPoint> = mutableListOf()
    private var pointsAlgo : MutableList<Vertex> = mutableListOf()
    private val curPoints : MutableList<MapPoint> = mutableListOf()

    private lateinit var svg: SVG
    lateinit var rectF: RectF

    private val paint : Paint = Paint()
    private val paint2 : Paint = Paint()

    private var xRatio : Double = 0.0
    private var yRatio : Double = 0.0

    val pointClicked = event<PointEventArgs>()

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        paint.strokeWidth = 3f

        paint2.style = Paint.Style.STROKE
        paint2.color = Color.BLUE
        paint2.strokeWidth = 3f
        paint2.isDither = true
        paint2.style = Paint.Style.STROKE
        paint2.strokeJoin = Paint.Join.ROUND
        paint2.strokeCap = Paint.Cap.ROUND
        paint2.pathEffect = CornerPathEffect(10F)
        paint2.isAntiAlias = true
    }

    fun initFloor(floorData : FloorData, buildingData : BuildingData) {
        fData = floorData
        bData = buildingData

        xRatio = (bData.width)/(3251.43 + 17.04)
        yRatio = (bData.height)/(3251.43 + 5*17.04)

        graph = XMLFloorParser(context,
            "buildings/b_${bData.id}/g_${fData.number}.svg").graph

        for (v in graph.vertices) {
            v.x = v.x * xRatio + bData.x
            v.y = v.y * yRatio + bData.y + 13 * yRatio
            if (v.id != "") {
                val p = MapPoint(context, v)
                points.add(p)
            }
        }

        algorithm = DijkstraAlgorithm(graph)

        svg = SVG.getFromAsset(context.assets,
            "buildings/b_${bData.id}/f_${fData.number}.svg")
        rectF = RectF(bData.x, bData.y,
            bData.x + bData.width,
            bData.y + (svg.documentViewBox.bottom - svg.documentViewBox.top) /
                    ((svg.documentViewBox.right - svg.documentViewBox.left) / bData.width))
    }

    override fun draw(p0: Canvas) {
//        p0.drawRect(rectF, paint)
        svg.renderToCanvas(p0, rectF)

        if (pointsAlgo.size > 0) {
            for (i in 0 until pointsAlgo.size - 1) {
                p0.drawLine(pointsAlgo[i].x.toFloat(),
                    pointsAlgo[i].y.toFloat(),
                    pointsAlgo[i + 1].x.toFloat(),
                    pointsAlgo[i + 1].y.toFloat(), paint2)
            }
        }

        for (p in points) {
            p.draw(p0)
        }
    }

    fun onTouchEvent(event: MotionEvent, x : Float, y: Float) {
        if (event.action == MotionEvent.ACTION_UP) {
            for (p in points) {
                if (p.r.contains(x, y)) {
                    p.onTouchEvent(event, x, y)

                    pointClicked(PointEventArgs(p.isActivate, p, fData, bData))

//                    if (p.isActivate)
//                        curPoints.add(p)
//                    else curPoints.remove(p)
//                    if (curPoints.size == 2) {
//                        pointsAlgo.addAll(algorithm.runAlgorithm(
//                            curPoints[0].vertex.id, curPoints[1].vertex.id))
//                    }
//                    else {
//                        pointsAlgo.clear()
//                        algorithm = DijkstraAlgorithm(graph)
//                    }
                    break
                }
            }
        }
    }

    override fun setAlpha(p0: Int) {
    }

    override fun setColorFilter(p0: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE;
    }
}

data class PointEventArgs(val isActivate: Boolean,
                          val point: MapPoint,
                          val floorData: FloorData,
                          val buildingData: BuildingData
)