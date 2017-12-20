package me.gcx11.spacegame.spaceship

import kotlin.math.cos
import kotlin.math.sin
import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.Entity

class MoveBehaviourComponent(
    override val parent: Entity,

    val speed: Float = 5f
) : BehaviourComponent {
    override fun update(delta: Float) {
        parent.getComponent<GeometricComponent>()?.let {
            it.x += speed * cos(it.directionAngle)
            it.y += speed * sin(it.directionAngle)
        }
    }
}