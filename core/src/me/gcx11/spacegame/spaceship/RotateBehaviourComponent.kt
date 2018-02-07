package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.Entity
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sign

class RotateBehaviourComponent(
    override val parent: Entity,

    val rotateSpeed: Float = 5f
) : BehaviourComponent {
    val threshold = PI / 90f

    override fun update(delta: Float) {
        parent.getOptionalComponent<GeometricComponent>()?.let {
                val mouseAngle = parent.getRequiredComponent<LogicComponent>().computeDirection()
                val diff = mouseAngle - it.directionAngle

                when {
                    abs(diff) < threshold -> it.directionAngle = mouseAngle
                    abs(diff) > PI -> it.directionAngle -= rotateSpeed * delta * diff.sign
                    else -> it.directionAngle += rotateSpeed * delta * diff.sign
                }

                if (it.directionAngle < -PI) {
                    it.directionAngle += 2 * PI.toFloat()
                } else if (it.directionAngle > PI) {
                    it.directionAngle -= 2 * PI.toFloat()
                }
            }
    }
}