package me.gcx11.spacegame.core.components

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Point

interface CameraComponent : BehaviourComponent {
    var x: Float
    var y: Float

    fun follow(entity: Entity)
    fun setDimensions(width: Float, height: Float)
    fun isVisible(entity: Entity): Boolean
    fun unproject(point: Point): Point
}