package me.gcx11.spacegame.meteor

import com.badlogic.gdx.graphics.Color
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent
import me.gcx11.spacegame.core.components.ShapeRenderableComponent

object MeteorSpawner {

    fun createMeteor(x: Float, y: Float, directionAngle: Float): Entity {
        return Entity.new().apply {
            addComponent(
                GeometricComponent(
                    this,
                    x,
                    y,
                    radius = 40f,
                    directionAngle = directionAngle
                )
            )
            addComponent(DefaultCollidableComponent(this))
            addComponent(ShapeRenderableComponent(this, Color.BROWN))
            addComponent(MoveComponent(this, speed = 0.5f))
        }
    }
}