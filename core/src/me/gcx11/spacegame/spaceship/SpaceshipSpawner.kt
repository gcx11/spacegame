package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.graphics.Color
import me.gcx11.spacegame.core.DefaultCollidableComponent
import me.gcx11.spacegame.core.Entity

object SpaceshipSpawner {

    fun createMinimalPrototype(x: Float, y: Float): Entity {
        return Entity.new().also {
            it.addComponent(GeometricComponent(it, x, y, 20f, 20f, 15f))
            it.addComponent(DefaultCollidableComponent(it))
        }
    }

    fun createPrototype(x: Float, y: Float, color: Color): Entity {
        return createMinimalPrototype(x, y).also {
            it.addComponent(RenderableComponent(it, color = color))
            it.addComponent(FireBehaviourComponent(it))
            it.addComponent(BoosterComponent(it))
        }
    }

    fun createPlayer(x: Float, y: Float): Entity {
        return createPrototype(x, y, Color.RED).also {
            it.addComponent(MoveBehaviourComponent(it))
            it.addComponent(PlayerLogicComponent(it))
            it.addComponent(RotateBehaviourComponent(it))
        }
    }

    fun createEnemy(x: Float, y: Float): Entity {
        return createPrototype(x, y, Color.ORANGE).also {
            it.addComponent(MoveBehaviourComponent(it, 4f))
            it.addComponent(EnemyLogicComponent(it))
            it.addComponent(RotateBehaviourComponent(it, 3f))
        }
    }
}