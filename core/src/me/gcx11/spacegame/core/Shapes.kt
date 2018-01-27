package me.gcx11.spacegame.core

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2

sealed class Shape {
    abstract fun intersectsWith(shape: Shape): Boolean
}

data class Point(
    var x: Float,
    var y: Float
) : Shape() {
    val vector: Vector2 by ReusableVector {
        x = this@Point.x
        y = this@Point.y
    }

    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> vector.epsilonEquals(shape.vector)
            is Line -> {
                val vectorAC = vector.cpy().sub(shape.first.vector)
                val vectorCB = shape.second.vector.cpy().sub(vector)
                val vectorAB = shape.second.vector.cpy().sub(shape.first.vector)

                vectorAB.len() == vectorAC.len() + vectorCB.len()
            }
            is Triangle -> this.isPointInsideTriangle(shape.first, shape.second, shape.third)
            is Complex -> shape.intersectsWith(this)
        }
    }

    override fun toString() = "Point ($x, $y)"
}

data class Line(
    var first: Point,
    var second: Point
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> shape.intersectsWith(this)
            is Line -> {
                if (shape.intersectsWith(first) || first.intersectsWith(shape)) return true
                else if (shape.intersectsWith(second) || second.intersectsWith(shape)) return true

                Intersector.intersectSegments(
                    first.vector, second.vector, shape.first.vector, shape.second.vector, null
                )
            }
            is Triangle -> {
                shape.intersectsWith(first) || shape.intersectsWith(second)
            }
            is Complex -> shape.intersectsWith(this)
        }
    }

    override fun toString() = "Line [(${first.x}, ${first.y}), (${second.x}, ${second.y})]"
}

data class Triangle(
    var first: Point,
    var second: Point,
    var third: Point
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> shape.intersectsWith(this)
            is Line -> shape.intersectsWith(this)
            is Triangle -> {
                vertices.any {
                    shape.intersectsWith(it)
                } || shape.vertices.any {
                    intersectsWith(it)
                }
            }
            is Complex -> shape.intersectsWith(this)
        }
    }

    val vertices get() = setOf(first, second, third)

    val edges get() = setOf(Line(first, second), Line(second, third), Line(third, first))

    override fun toString(): String {
        return "Triangle [(${first.x}, ${first.y}), (${second.x}, ${second.y}), (${third.x}, ${third.y})]"
    }
}

data class Complex(
    val subShapes: Set<Shape>
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> subShapes.any { shape.intersectsWith(it) }
            is Line -> subShapes.any { shape.intersectsWith(it) }
            is Triangle -> subShapes.any { shape.intersectsWith(it) }
            is Complex -> subShapes.any { shape.intersectsWith(it) }
        }
    }
}


fun Point.isPointInsideTriangle(p1: Point, p2: Point, p3: Point): Boolean {
    // check edges first
    val triangle = Triangle(p1, p2, p3)
    if (triangle.edges.any { it.intersectsWith(this) }) return true

    return Intersector.isPointInTriangle(vector, p1.vector, p2.vector, p3.vector)
}