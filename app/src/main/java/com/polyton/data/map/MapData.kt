package com.polyton.data.map

data class BuildingData(var id: Int = 0,
                   var name: String = "",
                   var abbr: String = "",
                   var address: String = "",
                   var x: Float = 0f,
                   var y: Float = 0f,
                   var width: Float = 0f,
                   var height: Float = 0f,
                   var floors: List<FloorData> = ArrayList())

data class FloorData(var number: Int = 0)

data class BuildingItems(var buildings: List<BuildingData> = ArrayList())