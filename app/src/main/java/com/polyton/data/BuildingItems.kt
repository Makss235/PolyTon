package com.polyton.data

data class BuildingData(private var id: Int,
                   private var name: String?,
                   private var abbr: String?,
                   private var address: String?,
                   private var x: Float,
                   private var y: Float,
                   private var width: Float,
                   private var height: Float,
                   private var floors: List<FloorData>?)

data class FloorData(private var number: Int)

data class BuildingItems(var buildings: List<BuildingData>? = null)