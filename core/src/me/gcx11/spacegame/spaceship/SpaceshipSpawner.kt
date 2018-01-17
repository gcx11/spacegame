package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.graphics.Color
import me.gcx11.spacegame.core.Entity

object SpaceshipSpawner {

    private fun createPrototype(x: Float, y: Float, color: Color): Entity {
        return Entity.new().also {
            it.addComponent(
                me.gcx11.spacegame.spaceship.GeometricComponent(
                    it, x, y, 20f, 5f, 15f
                )
            )
            it.addComponent(me.gcx11.spacegame.spaceship.RenderableComponent(it, color = color))
            it.addComponent(me.gcx11.spacegame.spaceship.FireBehaviourComponent(it))
            it.addComponent(me.gcx11.spacegame.spaceship.CollidableComponent(it))
            it.addComponent(me.gcx11.spacegame.spaceship.BoosterComponent(it))
        }
    }

    fun createPlayer(x: Float, y: Float): Entity {
        val playerSpaceship = createPrototype(x, y, Color.RED)
        playerSpaceship.let {
            it.addComponent(me.gcx11.spacegame.spaceship.RotateBehaviourComponent(it))
            it.addComponent(me.gcx11.spacegame.spaceship.MoveBehaviourComponent(it))
            it.addComponent(me.gcx11.spacegame.spaceship.PlayerLogicComponent(it))
        }

        return playerSpaceship
    }

    fun createEnemy(x: Float, y: Float): Entity {
        val enemySpaceship = createPrototype(x, y, Color.ORANGE)
        enemySpaceship.let {
            it.addComponent(me.gcx11.spacegame.spaceship.RotateBehaviourComponent(it, 3f))
            it.addComponent(me.gcx11.spacegame.spaceship.MoveBehaviourComponent(it, 4f))
            it.addComponent(me.gcx11.spacegame.spaceship.EnemyLogicComponent(it))
        }

        return enemySpaceship
    }
}