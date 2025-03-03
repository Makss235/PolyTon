package com.polyton.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.polyton.data.map.BuildingItems
import com.polyton.data.DataFromParsers
import com.polyton.data.parsers.ParserJson
import com.polyton.ui.widgets.map.MapView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataFromParsers.setBuildings(ParserJson().parseJson(applicationContext,
            "buildings.json", BuildingItems::class.java)?.buildings)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        val mapDrawLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        mapDrawLayoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        mapDrawLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        mapDrawLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        mapDrawLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

        val mapView = MapView(this)
        mapView.layoutParams = mapDrawLayoutParams

        val constraintLayout = ConstraintLayout(this)
        constraintLayout.addView(mapView)

        setContentView(constraintLayout)
    }
}