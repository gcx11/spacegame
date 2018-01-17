package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.Entity
import kotlin.math.cos
import kotlin.math.sin

private class MoveModifier(
    var percentage: Float,
    var duration: Float
)

class MoveBehaviourComponent(
    override val parent: Entity,

    val defaultSpeed: Float = 5f
) : BehaviourComponent {
    private var modifiers: MutableCollection<MoveModifier> = mutableListOf()

    val speed: Float get() = defaultSpeed * (1f + modifiers.map { it.percentage }.sum())

    override fun update(delta: Float) {
        parent.getOptionalComponent<GeometricComponent>()?.let {
                it.x += speed * cos(it.directionAngle)
                it.y += speed * sin(it.directionAngle)
            }

        modifiers.forEach { it.duration -= delta }
        modifiers.removeAll { it.duration < 0f }
    }

    fun addModifier(percentage: Float, duration: Float) {
        modifiers.add(MoveModifier(percentage, duration))
    }
}