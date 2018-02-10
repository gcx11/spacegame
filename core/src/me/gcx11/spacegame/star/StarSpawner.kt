package me.gcx11.spacegame.star

import com.badlogic.gdx.graphics.Color
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent

object StarSpawner {

    fun createStar(x: Float, y: Float): Entity {
        return Entity.new().apply {
            addComponent(GeometricComponent(this, x, y))
            addComponent(DefaultCollidableComponent(this))
            addComponent(RenderableComponent(this, Color.WHITE))
        }
    }
}