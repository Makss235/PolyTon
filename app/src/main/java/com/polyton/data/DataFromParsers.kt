package com.polyton.data

object DataFromParsers {

    private var building: List<BuildingData> = ArrayList<BuildingData>()

    fun getBuildings() : List<BuildingData> {
        return building
    }

    fun setBuildings(buildings: List<BuildingData>?) {
        building = buildings!!
    }
}