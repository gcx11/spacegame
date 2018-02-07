package me.gcx11.spacegame.meteor

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent

object MeteorSpawner {

    fun createMeteor(x: Float, y: Float): Entity {
        return Entity.new().also {
            it.addComponent(GeometricComponent(it, x, y, radius = 40f))
            it.addComponent(DefaultCollidableComponent(it))
            it.addComponent(RenderableComponent(it))
            it.addComponent(MoveBehaviourComponent(it))
        }
    }
}