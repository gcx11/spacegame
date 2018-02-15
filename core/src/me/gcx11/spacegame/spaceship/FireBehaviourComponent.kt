package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.bullet.BulletSpawner
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.components.getRequiredSibling

class FireBehaviourComponent(
    override val parent: Entity,

    val fireDelay: Float = 0.5f
) : BehaviourComponent {
    var fireTimer = 0f

    override fun update(delta: Float) {
        if (getRequiredSibling<LogicComponent>().canFire() && fireTimer <= 0f) {
            launchMissle()
            fireTimer = fireDelay
        }

        fireTimer -= delta
    }

    fun launchMissle() {
        SpaceGame.scene.addLater(BulletSpawner.createBullet(parent))
    }
}