package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.Entity
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

enum class EnemyBehaviourState(
    val priority: Int
) {

    ATTACKING(1) {
        var target: Entity? = null

        override fun needActivate(component: EnemyLogicComponent): Boolean {
            return hasPlayerInSight(component)
        }

        override fun updateLogic(component: EnemyLogicComponent) {
            if (target == null) target = player
        }

        override fun onDisable() {
            target = null
        }

        override fun computeDirection(component: EnemyLogicComponent): Float {
            val selfGeo = component.parent.getRequiredComponent<GeometricComponent>()
            val targetGeo = target?.getRequiredComponent<GeometricComponent>()

            if (targetGeo != null) {
                return atan2(targetGeo.y - selfGeo.y, targetGeo.x - selfGeo.x)
            } else {
                return selfGeo.directionAngle
            }
        }

        override fun computeSpeedPercentage(component: EnemyLogicComponent): Float {
            return if (isBehindEnemy(component)) 0.5f else 1.0f
        }

        override fun canSpeedUp(component: EnemyLogicComponent): Boolean {
            return isBehindEnemy(component)
        }

        override fun canFire(component: EnemyLogicComponent): Boolean {
            return isBehindEnemy(component)
        }

        val player: Entity?
            get() = SpaceGame.entitiesReadOnly.firstOrNull { it.hasComponent<PlayerLogicComponent>() }


        private fun hasPlayerInSight(component: EnemyLogicComponent): Boolean {
            val selfGeo = component.parent.getRequiredComponent<GeometricComponent>()
            val targetGeo = player?.getRequiredComponent<GeometricComponent>()

            if (targetGeo != null) {
                return hypot(abs(selfGeo.x - targetGeo.x), abs(selfGeo.y - targetGeo.y)) <= 200f
            } else {
                return false
            }
        }

        private fun isBehindEnemy(component: EnemyLogicComponent): Boolean {
            val selfGeo = component.parent.getRequiredComponent<GeometricComponent>()
            val targetGeo = target?.getRequiredComponent<GeometricComponent>()

            if (targetGeo != null) {
                val enemyDirection = atan2(targetGeo.y - selfGeo.y, targetGeo.x - selfGeo.x)
                return abs(selfGeo.directionAngle - enemyDirection) < 0.5f
            } else {
                return false
            }
        }
    },
    WAITING(0) {
        override fun needActivate(component: EnemyLogicComponent): Boolean {
            return true
        }

        override fun onDisable() {}

        override fun updateLogic(component: EnemyLogicComponent) {}

        override fun computeDirection(component: EnemyLogicComponent): Float {
            return 0f
        }

        override fun computeSpeedPercentage(component: EnemyLogicComponent): Float {
            return 0.2f
        }

        override fun canSpeedUp(component: EnemyLogicComponent): Boolean {
            return false
        }

        override fun canFire(component: EnemyLogicComponent): Boolean {
            return false
        }

    };

    abstract fun needActivate(component: EnemyLogicComponent): Boolean
    abstract fun onDisable()
    abstract fun updateLogic(component: EnemyLogicComponent)
    abstract fun computeDirection(component: EnemyLogicComponent): Float
    abstract fun computeSpeedPercentage(component: EnemyLogicComponent): Float
    abstract fun canSpeedUp(component: EnemyLogicComponent): Boolean
    abstract fun canFire(component: EnemyLogicComponent): Boolean
}