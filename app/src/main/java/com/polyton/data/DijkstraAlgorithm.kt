package com.polyton.data

import com.polyton.data.parsers.Graph
import com.polyton.data.parsers.Vertex

internal class DijkstraAlgorithm(private val graph: Graph) {

    fun runAlgorithm(source: Vertex, end: Vertex): List<Vertex> {
        val distances = mutableMapOf<Vertex, Double>().apply {
            graph.vertices.forEach { this[it] = Double.MAX_VALUE }
        }
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
}

class PathFinder(private val graph: Graph) {

    fun findAllPaths(source: Vertex, destination: Vertex): List<List<Vertex>> {
        val allPaths = mutableListOf<List<Vertex>>()
        val currentPath = mutableListOf<Vertex>()
        dfs(source, destination, currentPath, allPaths)
        return allPaths
    }

    private fun dfs(current: Vertex, destination: Vertex, currentPath: MutableList<Vertex>, allPaths: MutableList<List<Vertex>>) {
        currentPath.add(current)

        if (current == destination) {
            allPaths.add(ArrayList(currentPath))
        } else {
            for (edge in current.edges) {
                val nextVertex = if (edge.from == current) edge.to else edge.from
                if (!currentPath.contains(nextVertex)) {
                    dfs(nextVertex, destination, currentPath, allPaths)
                }
            }
        }

        currentPath.removeAt(currentPath.size - 1)
    }

    fun findShortestPath(source: Vertex, destination: Vertex): List<Vertex>? {
        val allPaths = findAllPaths(source, destination)

        return allPaths.minByOrNull { path -> calculatePathWeight(path) }
    }

    private fun calculatePathWeight(path: List<Vertex>): Double {
        var totalWeight = 0.0
        for (i in 0 until path.size - 1) {
            val from = path[i]
            val to = path[i + 1]
            totalWeight += from.edges.find { it.to == to || it.from == to }?.weight ?: Double.MAX_VALUE
        }
        return totalWeight
    }
}