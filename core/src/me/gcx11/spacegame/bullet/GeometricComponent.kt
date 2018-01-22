package me.gcx11.spacegame.bullet

import com.badlogic.gdx.math.Vector2
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent
import me.gcx11.spacegame.core.Line
import me.gcx11.spacegame.core.Shape
import kotlin.math.cos
import kotlin.math.sin

class GeometricComponent(
        override val parent: Entity,

        var x: Float,
        var y: Float,
        val directionAngle: Float = 0f,

        val size: Float = 5f
) : GeometricComponent {
    val endX get() = x + size * cos(directionAngle)
    val endY get() = y + size * sin(directionAngle)

    override val shape: Shape
        get() {
            return Line(
                    Vector2(x, y),
                    Vector2(endX, endY)
            )
        }

    override fun toString(): String =
            buildString {
                append("Bullet GC with:")
                append("\n\t")
                append("x: ${x}, ")
                append("y: ${x}, ")
                append("endX: ${endX}, ")
                append("endY: ${endY}, ")
                append("directionAngle: ${directionAngle}")
            }

}