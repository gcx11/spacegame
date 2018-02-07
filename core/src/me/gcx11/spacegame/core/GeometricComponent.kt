package me.gcx11.spacegame.core

interface GeometricComponent : Component {
    var x: Float
    var y: Float

    val shape: Shape
}