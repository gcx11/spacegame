package me.gcx11.spacegame.bullet

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultMoveComponent
import me.gcx11.spacegame.core.components.getRequiredSibling

class MoveComponent(
    override val parent: Entity,

    override var speed: Float = 5f
) : DefaultMoveComponent(parent, speed) {
    private var timer = 2f

    override fun update(delta: Float) {
        if (timer < 0) {
            SpaceGame.scene.deleteLater(parent)
        }

        timer -= delta
        super.update(delta)
    }

    override fun direction(): Float {
        return getRequiredSibling<GeometricComponent>().directionAngle
    }
}