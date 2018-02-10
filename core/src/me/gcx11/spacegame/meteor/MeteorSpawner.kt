package me.gcx11.spacegame.meteor

import com.badlogic.gdx.graphics.Color
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent
import me.gcx11.spacegame.core.components.ShapeRenderableComponent

object MeteorSpawner {

    fun createMeteor(x: Float, y: Float): Entity {
        return Entity.new().apply {
            addComponent(GeometricComponent(this, x, y, radius = 40f))
            addComponent(DefaultCollidableComponent(this))
            addComponent(ShapeRenderableComponent(this, Color.BROWN))
            addComponent(MoveBehaviourComponent(this))
        }
    }
}