package me.gcx11.spacegame.meteor

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultMoveComponent
import me.gcx11.spacegame.core.components.getRequiredSibling

class MoveComponent(
    override val parent: Entity,
    override var speed: Float = 0.5f
) : DefaultMoveComponent(parent, speed) {

    override fun direction(): Float {
        return getRequiredSibling<GeometricComponent>().directionAngle
    }
}