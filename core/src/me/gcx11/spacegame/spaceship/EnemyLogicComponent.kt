package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Entity

class EnemyLogicComponent(
    parent: Entity
) : LogicComponent(parent) {
    private var scripts = mutableListOf(
        WanderScript(this, 0),
        AttackScript(this, 1),
        AntiCollideScript(this, 2)
    )
    var currentScript: LogicScript? = null

    var direction: Float = 0f
    var canSpeedUp: Boolean = false
    var speedPercentage: Float = 0f
    var canFire: Boolean = false

    override fun update(delta: Float) {
        scripts.sortByDescending { it.priority }

        val newScript = scripts.firstOrNull { it.needActivation() }

        if (newScript != currentScript) {
            currentScript?.disable()
            currentScript = newScript
            currentScript?.enable()
        }

        currentScript?.update(delta)
    }

    override fun computeDirection(): Float {
        return direction
    }

    override fun canSpeedUp(): Boolean {
        return canSpeedUp
    }

    override fun computeSpeedPercentage(): Float {
        return speedPercentage
    }

    override fun canFire(): Boolean {
        return canFire
    }
}