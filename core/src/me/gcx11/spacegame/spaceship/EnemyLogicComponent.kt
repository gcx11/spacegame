package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.Entity
import kotlin.math.abs
import kotlin.math.atan2

class EnemyLogicComponent(
    parent: Entity
) : LogicComponent(parent) {
    private var target: Entity? = null

    override fun update(delta: Float) {
        target = SpaceGame.entitiesReadOnly.firstOrNull { it.hasComponent<PlayerLogicComponent>() }
    }

    override fun computeDirection(): Float {
        val selfGeo = parent.getRequiredComponent<GeometricComponent>()
        val targetGeo = target?.getRequiredComponent<GeometricComponent>()

        if (targetGeo != null) {
            return atan2(targetGeo.y - selfGeo.y, targetGeo.x - selfGeo.x)
        } else {
            return selfGeo.directionAngle
        }
    }

    override fun canFire(): Boolean {
        return isBehindEnemy()
    }

    override fun canSpeedUp(): Boolean {
        return isBehindEnemy()
    }

    override fun computeSpeedPercentage(): Float {
        return if (isBehindEnemy()) 0.5f else 1.0f
    }

    private fun isBehindEnemy(): Boolean {
        val selfGeo = parent.getRequiredComponent<GeometricComponent>()
        val targetGeo = target?.getRequiredComponent<GeometricComponent>()

        if (targetGeo != null) {
            val enemyDirection = atan2(targetGeo.y - selfGeo.y, targetGeo.x - selfGeo.x)
            return abs(selfGeo.directionAngle - enemyDirection) < 0.5f
        } else {
            return false
        }
    }
}