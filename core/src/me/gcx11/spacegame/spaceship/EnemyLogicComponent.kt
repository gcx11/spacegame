package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Entity

class EnemyLogicComponent(
    parent: Entity
) : LogicComponent(parent) {
    var currentState: EnemyBehaviourState = EnemyBehaviourState.WAITING

    override fun update(delta: Float) {
        val newState = states.first { it.needActivate(this) }

        if (newState != currentState) currentState.onDisable()
        currentState = newState

        currentState.updateLogic(this)
    }

    override fun computeDirection(): Float {
        return currentState.computeDirection(this)
    }

    override fun canSpeedUp(): Boolean {
        return currentState.canSpeedUp(this)
    }

    override fun computeSpeedPercentage(): Float {
        return currentState.computeSpeedPercentage(this)
    }

    override fun canFire(): Boolean {
        return currentState.canFire(this)
    }

    var states = arrayOf(EnemyBehaviourState.WAITING, EnemyBehaviourState.ATTACKING)
        .sortedByDescending { it.priority }
}