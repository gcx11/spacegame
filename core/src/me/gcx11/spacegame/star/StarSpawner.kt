package me.gcx11.spacegame.star

import me.gcx11.spacegame.core.DefaultCollidableComponent
import me.gcx11.spacegame.core.Entity

object StarSpawner {

    fun createStar(x: Float, y: Float): Entity {
        return Entity.new().also {
            it.addComponent(GeometricComponent(it, x, y))
            it.addComponent(DefaultCollidableComponent(it))
            it.addComponent(RenderableComponent(it))
        }
    }
}