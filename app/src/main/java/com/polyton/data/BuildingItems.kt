package com.polyton.data

data class BuildingData(var id: Int,
                   var name: String?,
                   var abbr: String?,
                   var address: String?,
                   var x: Float,
                   var y: Float,
                   var width: Float,
                   var height: Float,
                   var floors: List<FloorData>?)

data class FloorData(var number: Int)

data class BuildingItems(var buildings: List<BuildingData>? = null)