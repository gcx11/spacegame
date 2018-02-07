package me.gcx11.spacegame.core.components

import me.gcx11.spacegame.core.Shape

interface GeometricComponent : Component {
    var x: Float
    var y: Float

    val shape: Shape
}