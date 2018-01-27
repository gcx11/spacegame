package me.gcx11.spacegame.star

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Shape

class GeometricComponent(
    override val parent: Entity,

    val x: Float,
    val y: Float
) : GeometricComponent {
    override val shape: Shape get() = Point(x, y)
}