package com.polyton.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polyton.data.BuildingItems
import com.polyton.data.DataFromParsers
import com.polyton.data.parsers.ParserJson
import com.polyton.ui.map.MapView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataFromParsers.setBuildings(ParserJson().parseJson(applicationContext,
            "buildings.json", BuildingItems::class.java)?.buildings)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        setContentView(MapView(applicationContext))
    }
}