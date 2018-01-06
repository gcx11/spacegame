package me.gcx11.spacegame.bullet

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.Entity
import kotlin.math.cos
import kotlin.math.sin

class MoveBehaviourComponent(
    override val parent: Entity,

    val speed: Float = 10f
) : BehaviourComponent {
    var timer = 2f

    override fun update(delta: Float) {
        timer -= delta
        if (timer < 0) SpaceGame.deleteLater(parent)

        parent.getOptionalComponent<GeometricComponent>()?.let {
                it.x += speed * cos(it.directionAngle)
                it.y += speed * sin(it.directionAngle)
            }
    }
}