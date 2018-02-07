package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.Entity
import kotlin.math.cos
import kotlin.math.sin

private class MoveModifier(
    var percentage: Float,
    var duration: Float
)

class MoveBehaviourComponent(
    override val parent: Entity,

    val defaultSpeed: Float = 5f,
    val acceleration: Float = 0.02f,
    var currentSpeed: Float = 0f
) : BehaviourComponent {
    var movementPercentage: Float = 0.00f

    private var modifiers: MutableCollection<MoveModifier> = mutableListOf()

    val speed: Float get() = defaultSpeed * (1f + modifiers.map { it.percentage }.sum())

    override fun update(delta: Float) {
        parent.getOptionalComponent<GeometricComponent>()?.let {
                movementPercentage = parent.getOptionalComponent<LogicComponent>()
                    ?.computeSpeedPercentage() ?: 1.0f
                val desiredSpeed = speed * movementPercentage
                if (currentSpeed < desiredSpeed) {
                    currentSpeed += acceleration
                } else if (currentSpeed > desiredSpeed) {
                    currentSpeed -= acceleration
                }

                it.x += currentSpeed * cos(it.directionAngle)
                it.y += currentSpeed * sin(it.directionAngle)
            }

        modifiers.forEach { it.duration -= delta }
        modifiers.removeAll { it.duration < 0f }
    }

    fun addModifier(percentage: Float, duration: Float) {
        modifiers.add(MoveModifier(percentage, duration))
    }
}