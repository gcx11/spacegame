package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.components.CollidableComponent
import me.gcx11.spacegame.core.components.getRequiredSibling
import me.gcx11.spacegame.core.utils.PI_FLOAT
import kotlin.math.abs

class AntiCollideScript(
    component: EnemyLogicComponent, priority: Int
) : LogicScript(component, priority) {

    override fun needActivation(): Boolean {
        return isGoingToCollide()
    }

    override fun update(delta: Float) {
        val selfGeo = component.getRequiredSibling<GeometricComponent>()

        val pointToAvoid = getTooClose()
        component.speedPercentage = 0.3f
        component.canSpeedUp = false
        component.canFire = false

        if (pointToAvoid != null) {
            val closestPoint = selfGeo.shape.points().minBy { it.distanceTo(it) } ?: selfGeo.center
            val angleToPoint = closestPoint.angleWith(pointToAvoid)
            if (abs(angleToPoint - component.direction) < 10f) {
                var angle = closestPoint.angleWith(pointToAvoid) + PI_FLOAT
                if (angle > PI_FLOAT) angle -= 2 * PI_FLOAT
                else if (angle < -PI_FLOAT) angle += 2 * PI_FLOAT
                component.direction = angle
            }
        }
    }

    override fun enable() {
    }

    override fun disable() {
    }

    private fun isGoingToCollide(): Boolean {
        return getTooClose() != null
    }

    private fun getTooClose(): Point? {
        val selfGeo = component.getRequiredSibling<GeometricComponent>()

        return SpaceGame.scene.entitiesReadOnly
            .filter {
                it != component.parent && it.hasComponent<CollidableComponent>()
            }
            .flatMap {
                it.getRequiredComponent<me.gcx11.spacegame.core.components.GeometricComponent>().shape.points()
            }
            .filter { p ->
                selfGeo.shape.points().any { it.distanceTo(p) <= 30f }
            }
            .minBy { p ->
                selfGeo.shape.points()
                    .map { it.distanceTo(p) }
                    .min() ?: 1000f
            }
    }
}