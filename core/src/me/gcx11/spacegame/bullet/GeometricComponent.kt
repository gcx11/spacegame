package me.gcx11.spacegame.bullet

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.GeometricComponent

class GeometricComponent(
    override val parent: Entity,

    var x: Float,
    var y: Float,
    var directionAngle: Float,

    val size: Float = 5f
) : GeometricComponent