package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.components.getRequiredSibling

class BoosterComponent(
    override val parent: Entity,
    val delay: Float = 3f,
    val duration: Float = 2f,
    val speedGain: Float = 1f
) : BehaviourComponent {
    var speedGainTimer = 0f

    override fun update(delta: Float) {
        val logicComponent = getRequiredSibling<LogicComponent>()
        val moveBehaviourComponent = getRequiredSibling<MoveComponent>()
        if (logicComponent.canSpeedUp() && speedGainTimer <= 0f) {
            moveBehaviourComponent.addModifier(speedGain, duration)
            speedGainTimer = delay
        }

        speedGainTimer -= delta
    }
}