package me.gcx11.spacegame.core

interface CameraComponent : BehaviourComponent {
    var x: Float
    var y: Float

    fun follow(entity: Entity)
    fun setDimensions(width: Float, height: Float)
    fun isVisible(entity: Entity): Boolean
    fun unproject(point: Point): Point
}