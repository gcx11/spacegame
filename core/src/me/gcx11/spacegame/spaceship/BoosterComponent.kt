package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.Entity

class BoosterComponent(
    override val parent: Entity,
    val delay: Float = 3f,
    val duration: Float = 2f,
    val speedGain: Float = 1f
) : BehaviourComponent {
    var speedGainTimer = 0f

    override fun update(delta: Float) {
        val logicComponent = parent.getRequiredComponent<LogicComponent>()
        val moveBehaviourComponent = parent.getRequiredComponent<MoveBehaviourComponent>()
        if (logicComponent.canSpeedUp() && speedGainTimer <= 0f) {
            moveBehaviourComponent.addModifier(speedGain, duration)
            speedGainTimer = delay
        }

        speedGainTimer -= delta
    }
}