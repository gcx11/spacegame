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
                return vectorAB.len() == vectorAC.len() + vectorCB.len()
            }
            is Triangle -> position.isPointInsideTriangle(shape.first, shape.second, shape.third)
        }
    }
}

data class Line(
    val first: Vector2,
    val second: Vector2
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> shape.intersectsWith(this)
            is Line -> Intersector.intersectLines(
                first, second, shape.first, shape.second, null
            )
            is Triangle -> return shape.edges.any { it.intersectsWith(this) }
        }
    }
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
            is Triangle -> shape.vertices.any {
                it.isPointInsideTriangle(first, second, third)
            }
        }
    }

    val vertices get() = setOf(first, second, third)

    val edges get() = setOf(Line(first, second), Line(second, third), Line(third, first))
}

fun Vector2.isPointInsideTriangle(v1: Vector2, v2: Vector2, v3: Vector2): Boolean {
    // https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
    val t = this.x - v1.x
    val u = this.y - v1.y
    val v = ((v2.x - v1.x) * u - (v2.y - v1.y) * t) > 0

    if (((v3.x - v1.x) * u - (v3.y - v1.y) * t > 0) == v) {
        return false
    } else {
        return ((v3.x - v2.x) * (this.y - v2.y) - (v3.y - v2.y) * (this.x - v2.x) > 0) == v
    }
}