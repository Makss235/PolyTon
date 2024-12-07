package com.polyton.ui.map

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout

class MapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private val buttonLayerLinearLayout : LinearLayout
    private val mapDraw : MapDraw

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

        mapDraw = MapDraw(context)
        mapDraw.layoutParams = mapDrawLayoutParams
//        mapDraw.onCurrentBuildingChanged += { (buildingDraw) ->
//            buttonLayersReinit(buildingDraw) }
        buttonLayersReinit(mapDraw.currentBuilding)

        val constraintLayout = ConstraintLayout(context)
        constraintLayout.addView(mapDraw)
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
                mapDraw.invalidate()
            }

            buttonLayerLinearLayout.addView(b)
        }
    }
}