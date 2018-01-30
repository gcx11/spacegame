package me.gcx11.spacegame.meteor

import me.gcx11.spacegame.core.Composed
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent
import me.gcx11.spacegame.core.PI_FLOAT
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Reusable
import me.gcx11.spacegame.core.Triangle
import kotlin.math.cos
import kotlin.math.sin

class GeometricComponent(
    override val parent: Entity,

    var x: Float,
    var y: Float,
    val radius: Float,
    val directionAngle: Float = 0.3f
) : GeometricComponent {

    val parts = 5

    val center by Reusable(Point.default) {
        x = this@GeometricComponent.x
        y = this@GeometricComponent.y
    }

    val points by Reusable(Array(parts, { Point.default })) {
        for (i in 0 until parts) {
            val point = this[i]
            point.x = x + radius * cos(i * 2 * PI_FLOAT / parts)
            point.y = y + radius * sin(i * 2 * PI_FLOAT / parts)
        }
    }

    override val shape: Composed by Reusable(Composed.default) {
        if (subShapes.size != parts) {
            for (i in 0 until parts) subShapes.add(Triangle.default)
        }
        for (i in 0 until parts) {
            val triangle = subShapes[i] as Triangle
            triangle.first = center
            triangle.second = points[i]
            triangle.third = points[(i + 1) % parts]
        }
    }
}