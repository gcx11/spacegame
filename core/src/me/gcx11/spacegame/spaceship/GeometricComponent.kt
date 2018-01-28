package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.ComposedFromTwo
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Reusable
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

    val nose by Reusable(Point.default) {
        x = noseX
        y = noseY
    }

    val leftWing by Reusable(Point.default) {
        x = leftWingX
        y = leftWingY
    }

    val rightWing by Reusable(Point.default) {
        x = rightWingX
        y = rightWingY
    }

    val center by Reusable(Point.default) {
        x = this@GeometricComponent.x
        y = this@GeometricComponent.y
    }

    val leftPart by Reusable(Triangle.default) {
        first = nose
        second = leftWing
        third = center
    }

    val rightPart by Reusable(Triangle.default) {
        first = nose
        second = rightWing
        third = center
    }

    override val shape by Reusable(ComposedFromTwo.default) {
        first = leftPart
        second = rightPart
    }
}
