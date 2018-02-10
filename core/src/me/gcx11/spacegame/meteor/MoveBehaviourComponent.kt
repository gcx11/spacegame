package me.gcx11.spacegame.meteor

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.components.getOptionalSibling
import kotlin.math.cos
import kotlin.math.sin

class MoveBehaviourComponent(
    override val parent: Entity,

    val speed: Float = 0.5f
) : BehaviourComponent {
    override fun update(delta: Float) {
        getOptionalSibling<GeometricComponent>()?.let {
            it.x += speed * cos(it.directionAngle)
            it.y += speed * sin(it.directionAngle)
        }
    }
}