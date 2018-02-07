package me.gcx11.spacegame.meteor

import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.Entity
import kotlin.math.cos
import kotlin.math.sin

class MoveBehaviourComponent(
    override val parent: Entity,

    val speed: Float = 0.5f
) : BehaviourComponent {
    override fun update(delta: Float) {
        parent.getOptionalComponent<GeometricComponent>()?.let {
                it.x += speed * cos(it.directionAngle)
                it.y += speed * sin(it.directionAngle)
            }
    }
}