package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Complex
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.ReusablePoint
import me.gcx11.spacegame.core.ReusableTriangle
import me.gcx11.spacegame.core.Shape
import me.gcx11.spacegame.core.Triangle
import kotlin.math.cos
import kotlin.math.sin

class GeometricComponent(
    override val parent: Entity,

    var x: Float,
    var y: Float,

    val noseSize: Float,
    val backSize: Float,
    val wingSize: Float,

    var directionAngle: Float = 0f
) : GeometricComponent {
    val noseX get() = x + noseSize * cos(directionAngle)
    val noseY get() = y + noseSize * sin(directionAngle)

    val leftWingX get() = x - backSize * cos(directionAngle) - wingSize * sin(directionAngle)
    val leftWingY get() = y + wingSize * cos(directionAngle) - backSize * sin(directionAngle)

    val rightWingX get() = x - backSize * cos(directionAngle) + wingSize * sin(directionAngle)
    val rightWingY get() = y - wingSize * cos(directionAngle) - backSize * sin(directionAngle)

    var nose: Point by ReusablePoint {
        x = noseX
        y = noseY
    }

    var leftWing: Point by ReusablePoint {
        x = leftWingX
        y = leftWingY
    }

    var rightWing: Point by ReusablePoint {
        x = rightWingX
        y = rightWingY
    }

    var center: Point by ReusablePoint {
        x = this@GeometricComponent.x
        y = this@GeometricComponent.y
    }

    var leftPart: Triangle by ReusableTriangle {
        first = nose
        second = leftWing
        third = center
    }

    var rightPart: Triangle by ReusableTriangle {
        first = nose
        second = rightWing
        third = center
    }

    override val shape: Shape get() = Complex(setOf(leftPart, rightPart))
}
