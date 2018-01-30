package me.gcx11.spacegame.core

/**
 * Default collidable component - default settings for basic collidable
 *
 * This class as minimalistic collidable component
 * to avoid code redundancy.
 *
 * @param parent Entity to which instance belongs
 * @param collidedCollection collection of collided components.
 * @sample [me.gcx11.spacegame.bullet.BulletSpawner.createBullet]
 */
class DefaultCollidableComponent(
    override val parent: Entity,
    override val collidedCollection: MutableCollection<CollidableComponent> = mutableListOf()
) : CollidableComponent