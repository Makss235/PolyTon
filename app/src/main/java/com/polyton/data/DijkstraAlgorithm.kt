package com.polyton.data

import com.polyton.data.parsers.Graph
import com.polyton.data.parsers.Vertex

internal class DijkstraAlgorithm(private val graph: Graph) {
    private val distances = mutableMapOf<Vertex, Double>().apply {
        graph.vertices.forEach { this[it] = Double.MAX_VALUE }
    }

    fun runAlgorithm(source: Vertex, end: Vertex): List<Vertex> {
        val previous = mutableMapOf<Vertex, Vertex>()
        val notVisited = graph.vertices.toMutableSet()

        distances[source] = 0.0

        while (notVisited.isNotEmpty()) {
            val nearestVertex = notVisited.minByOrNull { distances[it] ?: Double.MAX_VALUE }
            notVisited.remove(nearestVertex)

            nearestVertex?.edges?.forEach { edge ->
                val neighbor = if (edge.to == nearestVertex) edge.from else edge.to
                if (neighbor in notVisited) {
                    val currentDistance = (distances[nearestVertex] ?: Double.MAX_VALUE) + edge.weight
                    if (currentDistance < (distances[neighbor] ?: Double.MAX_VALUE)) {
                        distances[neighbor] = currentDistance
                        previous[neighbor] = nearestVertex
                    }
                }
            }
        }

        val path = mutableListOf<Vertex>()
        var current = end
        while (current != source) {
            path.add(current)
            current = previous[current] ?: previous.filterValues { it == current }.keys.first()
        }
        path.add(source)
        path.reverse()

        return path
    }

    fun runAlgorithm(source: String, end: String): List<Vertex> {
        return runAlgorithm(graph.vertices.find { it.id == source }!!, graph.vertices.find { it.id == end }!!)
    }
}