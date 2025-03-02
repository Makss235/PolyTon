package com.polyton.data

import com.polyton.data.map.BuildingData

object DataFromParsers {

    private var building: List<BuildingData> = ArrayList<BuildingData>()

    fun getBuildings() : List<BuildingData> {
        return building
    }

    fun setBuildings(buildings: List<BuildingData>?) {
        building = buildings!!
    }
}