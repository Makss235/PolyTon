package com.polyton.data.parsers

import android.content.Context
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.math.pow
import kotlin.math.sqrt


data class Vertex(
    var id: String = "",
    val x: Double,
    val y: Double,
    var edges: MutableList<Edge> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vertex) return false
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        return id.hashCode() * 31 + x.hashCode() * 31 + y.hashCode()
    }

    override fun toString(): String {
        return "Vertex(id='$id', x=$x, y=$y, size_edges=${edges.size})"
    }
}

data class Edge(
    val from: Vertex,
    val to: Vertex,
    var weight: Double = 0.0
) {
    init {
        weight = sqrt((to.x - from.x).pow(2.0) + (to.y - from.y).pow(2.0))
    }
}

class Graph {
    val edges: MutableList<Edge> = mutableListOf()
    val vertices: MutableList<Vertex> = mutableListOf()
}

class XMLFloorParser(private val context: Context, path: String) {

    private val _vertices: MutableList<Vertex> = mutableListOf()
    private val _edges: MutableList<Edge> = mutableListOf()
    lateinit var graph: Graph

    init {
        //TODO: try-catch
        parseXML(loadXml(path))


        graph.edges.forEach { Log.v("edges", it.to.toString()+" "+it.from.toString()) }
    }

    private fun parseXML(document: Document) {
        val nodes = document.documentElement
        val node = nodes.getElementsByTagName("g").item(0) as Element

        for (i in 0 until node.childNodes.length) {
            val child = node.childNodes.item(i)
            if (child.nodeType == Node.ELEMENT_NODE) {
                val element = child as Element
                when (element.tagName) {
                    "circle" -> {
                        val v = Vertex(
                            id = element.getAttribute("id"),
                            x = element.getAttribute("cx").toDouble(),
                            y = element.getAttribute("cy").toDouble()
                        )
                        if (!_vertices.any { it.x == v.x && it.y == v.y }) {
                            _vertices.add(v)
                        } else {
                            _vertices.find { it.x == v.x && it.y == v.y }?.let {
                                if (it.id.isEmpty()) it.id = v.id
                            }
                        }
                    }
                }
            }
        }

        for (i in 0 until node.childNodes.length) {
            val child = node.childNodes.item(i)
            if (child.nodeType == Node.ELEMENT_NODE) {
                val element = child as Element
                when (element.tagName) {
                    "line" -> {
                        val v1 = Vertex(
                            x = element.getAttribute("x1").toDouble(),
                            y = element.getAttribute("y1").toDouble()
                        )
                        val existingV1 = _vertices.find { it.x == v1.x && it.y == v1.y } ?: v1
                        if (v1 == existingV1) _vertices.add(v1)

                        val v2 = Vertex(
                            x = element.getAttribute("x2").toDouble(),
                            y = element.getAttribute("y2").toDouble()
                        )
                        val existingV2 = _vertices.find { it.x == v2.x && it.y == v2.y } ?: v2
                        if (v2 == existingV2) _vertices.add(v2)

                        val e = Edge(existingV1, existingV2)
                        if (!_edges.any { it.from == e.from && it.to == e.to }) {
                            _edges.add(e)
                        }
                    }
                }
            }
        }

        _vertices.forEach { vertex ->
//            vertex.edges = _edges.filter {
//                it.from == vertex || it.to == vertex
//            }.toMutableList()
            for (e in _edges) {
                if (e.to == vertex || e.from == vertex)
                    vertex.edges.add(e)
            }
        }

        graph = Graph().apply {
            vertices.addAll(_vertices)
            edges.addAll(_edges)
        }
    }

    private fun loadXml(path: String): Document {
        val inputStream: InputStream = context.assets.open(path)

        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        return documentBuilder.parse(inputStream).apply {
            documentElement.normalize()
        }
    }
}