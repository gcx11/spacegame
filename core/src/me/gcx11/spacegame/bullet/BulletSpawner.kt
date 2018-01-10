package me.gcx11.spacegame.bullet

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.spaceship.GeometricComponent
import me.gcx11.spacegame.spaceship.RenderableComponent

object BulletSpawner {

    fun createBullet(shooter: Entity): Entity {
        val shooterGeo = shooter.getRequiredComponent<GeometricComponent>()
        val shooterColor = shooter.getRequiredComponent<RenderableComponent>().color

        return Entity.new().also {
            it.addComponent(
                me.gcx11.spacegame.bullet.GeometricComponent(
                    it, shooterGeo.noseX, shooterGeo.noseY, shooterGeo.directionAngle
                )
            )
            it.addComponent(me.gcx11.spacegame.bullet.MoveBehaviourComponent(it))
            it.addComponent(me.gcx11.spacegame.bullet.RenderableComponent(it, color = shooterColor))
        }
    }
}