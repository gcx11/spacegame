package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.Entity

class FireBehaviourComponent(
    override val parent: Entity,

    val fireDelay: Float = 0.5f
) : BehaviourComponent {
    var fireTimer = 0f

    override fun update(delta: Float) {
        if (parent.getRequiredComponent<LogicComponent>().canFire() && fireTimer <= 0f) {
            launchMissle()
            fireTimer = fireDelay
        }

        fireTimer -= delta
    }

    fun launchMissle() {
        parent.getOptionalComponent<GeometricComponent>()?.let { p ->
            val ent = Entity.new().also {
                it.addComponent(me.gcx11.spacegame.bullet.GeometricComponent(
                        it, p.noseX, p.noseY, p.directionAngle))
                it.addComponent(me.gcx11.spacegame.bullet.MoveBehaviourComponent(it))
                it.addComponent(me.gcx11.spacegame.bullet.RenderableComponent(it))
            }
            SpaceGame.addLater(ent)
        }
    }
}