package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import me.gcx11.spacegame.core.Entity
import kotlin.math.atan2

class PlayerLogicComponent(
    parent: Entity
) : LogicComponent(parent) {

    override fun update(delta: Float) {
    }

    override fun computeDirection(): Float {
        val mouseX = Gdx.input.x
        val mouseY = Gdx.graphics.height - 1f - Gdx.input.y

        return atan2(mouseY - Gdx.graphics.height / 2f, mouseX - Gdx.graphics.width / 2f)
    }

    override fun canFire(): Boolean {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT)
    }
}