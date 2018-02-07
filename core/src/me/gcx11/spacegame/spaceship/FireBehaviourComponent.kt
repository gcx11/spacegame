package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.bullet.BulletSpawner
import me.gcx11.spacegame.core.components.BehaviourComponent
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
        SpaceGame.addLater(BulletSpawner.createBullet(parent))
    }
}