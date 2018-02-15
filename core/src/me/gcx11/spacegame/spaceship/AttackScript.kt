package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.getRequiredSibling
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

class AttackScript(
    component: EnemyLogicComponent,
    priority: Int
) : LogicScript(component, priority) {

    var target: Entity? = null

    override fun needActivation(): Boolean {
        return hasPlayerInSight()
    }

    override fun update(delta: Float) {
        val isBehindEnemy = isBehindEnemy()

        component.canFire = isBehindEnemy
        component.canSpeedUp = !isBehindEnemy
        component.direction = computeDirection()
        component.speedPercentage = 1.0f
    }

    override fun enable() {
        target = findTarget()
    }

    override fun disable() {
        target = null
    }

    private fun findTarget(): Entity? {
        return SpaceGame.scene.entitiesReadOnly.firstOrNull { it.hasComponent<PlayerLogicComponent>() }
    }

    private fun hasPlayerInSight(): Boolean {
        val selfGeo = component.getRequiredSibling<GeometricComponent>()
        val enemy = target ?: findTarget()
        val targetGeo = enemy?.getRequiredComponent<GeometricComponent>()

        if (targetGeo != null) {
            return hypot(abs(selfGeo.x - targetGeo.x), abs(selfGeo.y - targetGeo.y)) <= 500f
        } else {
            return false
        }
    }

    private fun isBehindEnemy(): Boolean {
        val selfGeo = component.getRequiredSibling<GeometricComponent>()
        val targetGeo = target?.getRequiredComponent<GeometricComponent>()

        if (targetGeo != null) {
            val enemyDirection = atan2(targetGeo.y - selfGeo.y, targetGeo.x - selfGeo.x)
            return abs(selfGeo.directionAngle - enemyDirection) < 0.5f
        } else {
            return false
        }
    }

    private fun computeDirection(): Float {
        val selfGeo = component.getRequiredSibling<GeometricComponent>()
        val targetGeo = target?.getRequiredComponent<GeometricComponent>()

        if (targetGeo != null) {
            return atan2(targetGeo.y - selfGeo.y, targetGeo.x - selfGeo.x)
        } else {
            return selfGeo.directionAngle
        }
    }

}