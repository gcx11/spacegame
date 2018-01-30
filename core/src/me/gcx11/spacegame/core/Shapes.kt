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
    val vector by Reusable(Vector2.Zero.cpy()) {
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
            is Triangle -> this.isPointInsideTriangle(shape)
            is ComposedFromTwo -> shape.intersectsWith(this)
            is Composed -> shape.intersectsWith(this)
        }
    }

    override fun toString() = "Point ($x, $y)"

    companion object {
        val default get() = Point(0f, 0f)
    }
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
            is ComposedFromTwo -> shape.intersectsWith(this)
            is Composed -> shape.intersectsWith(this)
        }
    }

    override fun toString() = "Line [(${first.x}, ${first.y}), (${second.x}, ${second.y})]"

    companion object {
        val default get() = Line(Point.default, Point.default)
    }
}

data class Triangle(
    var first: Point,
    var second: Point,
    var third: Point
) : Shape() {
    val firstLine by Reusable(Line(Point.default, Point.default)) {
        first = this@Triangle.first
        second = this@Triangle.second
    }

    val secondLine by Reusable(Line(Point.default, Point.default)) {
        first = this@Triangle.second
        second = this@Triangle.third
    }

    val thirdLine by Reusable(Line(Point.default, Point.default)) {
        first = this@Triangle.third
        second = this@Triangle.first
    }

    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> shape.intersectsWith(this)
            is Line -> shape.intersectsWith(this)
            is Triangle -> {
                shape.first.isPointInsideTriangle(this) ||
                    shape.second.isPointInsideTriangle(this) ||
                    shape.third.isPointInsideTriangle(this) ||
                    first.isPointInsideTriangle(shape) ||
                    second.isPointInsideTriangle(shape) ||
                    first.isPointInsideTriangle(shape)
            }
            is ComposedFromTwo -> shape.intersectsWith(this)
            is Composed -> shape.intersectsWith(this)
        }
    }

    val vertices get() = arrayOf(first, second, third)

    val edges get() = arrayOf(firstLine, secondLine, thirdLine)

    override fun toString(): String {
        return "Triangle [(${first.x}, ${first.y}), (${second.x}, ${second.y}), (${third.x}, ${third.y})]"
    }

    companion object {
        val default get() = Triangle(Point.default, Point.default, Point.default)
    }
}

data class ComposedFromTwo(
    var first: Shape,
    var second: Shape
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> shape.intersectsWith(first) || shape.intersectsWith(second)
            is Line -> shape.intersectsWith(first) || shape.intersectsWith(second)
            is Triangle -> shape.intersectsWith(first) || shape.intersectsWith(second)
            is ComposedFromTwo -> shape.intersectsWith(first) || shape.intersectsWith(second)
            is Composed -> shape.intersectsWith(first) || shape.intersectsWith(second)
        }
    }

    companion object {
        val default get() = ComposedFromTwo(Point.default, Point.default)
    }
}

data class Composed(
    val subShapes: MutableList<Shape>
) : Shape() {
    override fun intersectsWith(shape: Shape): Boolean {
        return when (shape) {
            is Point -> subShapes.any { it.intersectsWith(shape) }
            is Line -> subShapes.any { it.intersectsWith(shape) }
            is Triangle -> subShapes.any { it.intersectsWith(shape) }
            is ComposedFromTwo -> subShapes.any { it.intersectsWith(shape) }
            is Composed -> subShapes.any { it.intersectsWith(shape) }
        }
    }

    companion object {
        val default get() = Composed(mutableListOf())
    }
}


fun Point.isPointInsideTriangle(triangle: Triangle): Boolean {
    // check edges first
    if (triangle.firstLine.intersectsWith(this)) return true
    else if (triangle.secondLine.intersectsWith(this)) return true
    else if (triangle.thirdLine.intersectsWith(this)) return true

    return Intersector.isPointInTriangle(
        vector, triangle.first.vector, triangle.second.vector, triangle.third.vector
    )
}