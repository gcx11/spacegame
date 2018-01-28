package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Complex
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent
import me.gcx11.spacegame.core.ReusablePoint
import me.gcx11.spacegame.core.ReusableTriangle
import me.gcx11.spacegame.core.Shape
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

    val nose by ReusablePoint {
        x = noseX
        y = noseY
    }

    val leftWing by ReusablePoint {
        x = leftWingX
        y = leftWingY
    }

    val rightWing by ReusablePoint {
        x = rightWingX
        y = rightWingY
    }

    val center by ReusablePoint {
        x = this@GeometricComponent.x
        y = this@GeometricComponent.y
    }

    val leftPart by ReusableTriangle {
        first = nose
        second = leftWing
        third = center
    }

    val rightPart by ReusableTriangle {
        first = nose
        second = rightWing
        third = center
    }

    override val shape: Shape get() = Complex(setOf(leftPart, rightPart))
}
