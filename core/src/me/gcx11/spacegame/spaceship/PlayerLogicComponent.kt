package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector3
import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.Entity
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.min

class PlayerLogicComponent(
    parent: Entity
) : LogicComponent(parent) {

    override fun update(delta: Float) {
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

    override fun computeSpeedPercentage(): Float {
        val vec = SpaceGame.camera.unproject(
            Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        )

        val geo = parent.getRequiredComponent<GeometricComponent>()
        val screenMin = min(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)

        val result = min(screenMin, hypot(abs(geo.x - vec.x), abs(geo.y - vec.y))) / screenMin
        return result
    }
}