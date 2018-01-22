package me.gcx11.spacegame.core

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2

sealed class Shape {
    abstract fun intersectsWith(shape: Shape): Boolean
}

data class Point(
    val position: Vector2
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> position.epsilonEquals(shape.position)
            is Line -> {
                val vectorAC = position.cpy().sub(shape.first)
                val vectorCB = shape.second.cpy().sub(position)
                val vectorAB = shape.second.cpy().sub(shape.first)

                vectorAB.len() == vectorAC.len() + vectorCB.len()
            }
            is Triangle -> position.isPointInsideTriangle(shape.first, shape.second, shape.third)
            is Complex -> shape.intersectsWith(this)
        }
    }

    override fun toString() = "Point (${position.x}, ${position.y})"
}

data class Line(
    val first: Vector2,
    val second: Vector2
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> shape.intersectsWith(this)
            is Line -> {
                if (shape.intersectsWith(Point(first)) || Point(first).intersectsWith(shape)) return true
                else if (shape.intersectsWith(Point(second)) || Point(second).intersectsWith(shape)) return true

                Intersector.intersectSegments(
                    first, second, shape.first, shape.second, null
                )
            }
            is Triangle -> {
                shape.edges.any { it.intersectsWith(this) }
            }
            is Complex -> shape.intersectsWith(this)

        }
    }

    override fun toString() = "Line [(${first.x}, ${first.y}), (${second.x}, ${second.y})]"
}

data class Triangle(
    val first: Vector2,
    val second: Vector2,
    val third: Vector2
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> shape.intersectsWith(this)
            is Line -> shape.intersectsWith(this)
            is Triangle -> {
                edges.any {
                    it.intersectsWith(shape)
                } || shape.edges.any {
                    it.intersectsWith(this)
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


fun Vector2.isPointInsideTriangle(v1: Vector2, v2: Vector2, v3: Vector2): Boolean {
    // check edges first
    val triangle = Triangle(v1, v2, v3)
    if (triangle.edges.any { it.intersectsWith(Point(Vector2(x, y))) }) return true

    return Intersector.isPointInTriangle(this, v1, v2, v3)
}