package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector3
import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.Entity
import kotlin.math.atan2

class PlayerLogicComponent(
    parent: Entity
) : LogicComponent(parent) {

    override fun update(delta: Float) {
        parent.getRequiredComponent<CollidableComponent>().allCollided().forEach {
                println("Collided ${parent.id} with ${it.parent.id}")
            }
    }

    override fun computeDirection(): Float {
        val vec = SpaceGame.camera.unproject(
            Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        )
        val geo = parent.getRequiredComponent<GeometricComponent>()

        return atan2(vec.y - geo.y, vec.x - geo.x)
    }

    override fun canFire(): Boolean {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT)
    }

    override fun canSpeedUp(): Boolean {
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT)
    }
}