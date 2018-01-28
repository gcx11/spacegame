package me.gcx11.spacegame.bullet

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent
import me.gcx11.spacegame.core.ReusableLine
import me.gcx11.spacegame.core.ReusablePoint
import kotlin.math.cos
import kotlin.math.sin

class GeometricComponent(
    override val parent: Entity,

    var x: Float,
    var y: Float,
    var directionAngle: Float = 0f,

    val size: Float = 5f
) : GeometricComponent {
    val endX get() = x + size * cos(directionAngle)
    val endY get() = y + size * sin(directionAngle)

    val first by ReusablePoint {
        x = this@GeometricComponent.x
        y = this@GeometricComponent.y
    }

    val second by ReusablePoint {
        x = endX
        y = endY
    }

    override val shape by ReusableLine {
        first = this@GeometricComponent.first
        second = this@GeometricComponent.second
    }

    override fun toString(): String {
        return buildString {
            append("Bullet GC with:")
            append("\n")
            append("x: $x, ")
            append("y: $y, ")
            append("endX: $endX, ")
            append("endY: $endY, ")
            append("directionAngle: $directionAngle")
        }
    }
}