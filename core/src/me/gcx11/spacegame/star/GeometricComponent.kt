package me.gcx11.spacegame.star

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Reusable
import me.gcx11.spacegame.core.Shape
import me.gcx11.spacegame.core.components.GeometricComponent

class GeometricComponent(
    override val parent: Entity,

    override var x: Float,
    override var y: Float
) : GeometricComponent {

    override val shape: Shape by Reusable(Point.default) {
        x = this@GeometricComponent.x
        y = this@GeometricComponent.y
    }
}