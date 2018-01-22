package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.math.Vector2
import me.gcx11.spacegame.core.*
import me.gcx11.spacegame.core.GeometricComponent
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
    val rightWingX get() = x - wingSize * cos(directionAngle) + backSize * sin(directionAngle)
    val rightWingY get() = y - backSize * cos(directionAngle) - wingSize * sin(directionAngle)

    override val shape: Shape
        get() {
            val leftTriangle = Triangle(
                    Vector2(noseX, noseY),
                    Vector2(leftWingX, leftWingY),
                    Vector2(x, y)
            )

            val rightTriangle = Triangle(
                    Vector2(noseX, noseY),
                    Vector2(rightWingX, rightWingY),
                    Vector2(x, y)
            )
            return Complex(setOf(leftTriangle, rightTriangle))
        }
}
