package me.gcx11.spacegame.spaceship

import kotlin.math.cos
import kotlin.math.sin
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent

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
}