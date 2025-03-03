package com.polyton.ui.widgets.map

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.polyton.ui.map.draw.BuildingDraw
import com.polyton.ui.map.draw.MapPoint
import com.polyton.ui.map.draw.PointEventArgs

class MapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private val buttonLayerLinearLayout : LinearLayout
    private val mapWidget : MapWidget

    private lateinit var firstPoint : MapPoint
    private lateinit var secondPoint : MapPoint

    init {
        val mapDrawLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        mapDrawLayoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        mapDrawLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        mapDrawLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        mapDrawLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID


        val buttonLayerLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        buttonLayerLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        buttonLayerLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        buttonLayerLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

        buttonLayerLinearLayout = LinearLayout(context)
        buttonLayerLinearLayout.orientation = LinearLayout.VERTICAL
        buttonLayerLinearLayout.layoutParams = buttonLayerLayoutParams

        mapWidget = MapWidget(context)
//        mapWidget.pointClicked += { pointClicked(it) }
        mapWidget.layoutParams = mapDrawLayoutParams
//        buttonLayersReinit(mapWidget.currentBuilding)

        val constraintLayout = ConstraintLayout(context)
        constraintLayout.addView(mapWidget)
        constraintLayout.addView(buttonLayerLinearLayout)

        addView(constraintLayout)
    }

    private fun buttonLayersReinit(buildingDraw: BuildingDraw) {
        buttonLayerLinearLayout.removeAllViews()

        for (number in 1..buildingDraw.floorDraws.size) {
            val b = Button(context)
            b.setLayoutParams(LinearLayout.LayoutParams(150, 150))
            b.text = number.toString()
            b.setOnClickListener {
                buildingDraw.currentFloor = buildingDraw.floorDraws[number - 1]
                mapWidget.invalidate()
            }

            buttonLayerLinearLayout.addView(b)
        }
    }

    private fun pointClicked(args: PointEventArgs) {
        val dialog = BottomSheetDialog(context)

        val b1: Button = Button(context)
        b1.text = "1"
        b1.setOnClickListener {
            firstPoint = args.point
            args.point.answer(true)
            dialog.dismiss()
        }
        val b2: Button = Button(context)
        b2.text = "2"
        b2.setOnClickListener {
            secondPoint = args.point
            args.point.answer(true)
            dialog.dismiss()
        }

        val l = LinearLayout(context)
        l.addView(b1)
        l.addView(b2)

        dialog.setContentView(l)
        dialog.show()
    }
}