package me.gcx11.spacegame.bullet

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent
import me.gcx11.spacegame.spaceship.GeometricComponent
import me.gcx11.spacegame.spaceship.MoveBehaviourComponent
import me.gcx11.spacegame.spaceship.RenderableComponent
import kotlin.math.cos
import kotlin.math.sin

object BulletSpawner {

    fun createBullet(shooter: Entity): Entity {
        val shooterGeo = shooter.getRequiredComponent<GeometricComponent>()
        val shooterMove = shooter.getRequiredComponent<MoveBehaviourComponent>()
        val shooterColor = shooter.getRequiredComponent<RenderableComponent>().color

        return Entity.new().apply {
            addComponent(
                me.gcx11.spacegame.bullet.GeometricComponent(
                    this, shooterGeo.noseX + 2f * cos(shooterGeo.directionAngle),
                    shooterGeo.noseY + 2f * sin(shooterGeo.directionAngle),
                    shooterGeo.directionAngle
                )
            )
            addComponent(
                me.gcx11.spacegame.bullet.MoveBehaviourComponent(
                    this,
                    speed = shooterMove.speed + 2f
                )
            )
            addComponent(me.gcx11.spacegame.bullet.RenderableComponent(this, color = shooterColor))
            addComponent(DefaultCollidableComponent(this))
        }
    }
}