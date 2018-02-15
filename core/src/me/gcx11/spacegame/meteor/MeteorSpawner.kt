package me.gcx11.spacegame.meteor

import com.badlogic.gdx.graphics.Color
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent
import me.gcx11.spacegame.core.components.ShapeRenderableComponent

object MeteorSpawner {

    fun createMeteor(x: Float, y: Float, directionAngle: Float, rotationAngle: Float): Entity {
        return Entity.new().apply {
            addComponent(
                GeometricComponent(
                    this,
                    x,
                    y,
                    40f,
                    directionAngle,
                    rotationAngle
                )
            )
            addComponent(DefaultCollidableComponent(this))
            addComponent(ShapeRenderableComponent(this, Color.BROWN))
            addComponent(MoveComponent(this, speed = 0.5f))
        }
    }
}