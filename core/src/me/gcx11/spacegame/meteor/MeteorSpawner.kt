package me.gcx11.spacegame.meteor

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent

object MeteorSpawner {

    fun createMeteor(x: Float, y: Float): Entity {
        return Entity.new().apply {
            addComponent(GeometricComponent(this, x, y, radius = 40f))
            addComponent(DefaultCollidableComponent(this))
            addComponent(RenderableComponent(this))
            addComponent(MoveBehaviourComponent(this))
        }
    }
}