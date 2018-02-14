package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.graphics.Color
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultCollidableComponent
import me.gcx11.spacegame.core.components.ShapeRenderableComponent

object SpaceshipSpawner {

    fun createMinimalPrototype(x: Float, y: Float): Entity {
        return Entity.new().apply {
            addComponent(GeometricComponent(this, x, y, 20f, 20f, 15f))
            addComponent(DefaultCollidableComponent(this))
        }
    }

    fun createPrototype(x: Float, y: Float, color: Color): Entity {
        return createMinimalPrototype(x, y).apply {
            addComponent(ShapeRenderableComponent(this, color = color))
            addComponent(FireBehaviourComponent(this))
            addComponent(BoosterComponent(this))
        }
    }

    fun createPlayer(x: Float, y: Float): Entity {
        return createPrototype(x, y, Color.RED).apply {
            addComponent(MoveComponent(this, 5f))
            addComponent(PlayerLogicComponent(this))
            addComponent(RotateBehaviourComponent(this))
        }
    }

    fun createEnemy(x: Float, y: Float): Entity {
        return createPrototype(x, y, Color.ORANGE).apply {
            addComponent(MoveComponent(this, 4f))
            addComponent(EnemyLogicComponent(this))
            addComponent(RotateBehaviourComponent(this, 3f))
        }
    }
}