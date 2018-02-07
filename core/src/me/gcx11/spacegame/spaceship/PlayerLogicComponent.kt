package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.components.CameraComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Point
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
        val point = SpaceGame.camera.getRequiredComponent<CameraComponent>().unproject(
            Point(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
        )
        val geo = parent.getRequiredComponent<GeometricComponent>()

        return atan2(point.y - geo.y, point.x - geo.x)
    }

    override fun canFire(): Boolean {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT)
    }

    override fun canSpeedUp(): Boolean {
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT)
    }

    override fun computeSpeedPercentage(): Float {
        val point = SpaceGame.camera.getRequiredComponent<CameraComponent>().unproject(
            Point(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
        )

        val geo = parent.getRequiredComponent<GeometricComponent>()
        val screenMin = min(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)

        return min(screenMin, hypot(abs(geo.x - point.x), abs(geo.y - point.y))) / screenMin
    }
}