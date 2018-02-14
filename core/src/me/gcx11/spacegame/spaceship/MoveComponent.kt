package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DefaultMoveComponent
import me.gcx11.spacegame.core.components.getOptionalSibling
import me.gcx11.spacegame.core.components.getRequiredSibling

private class MoveModifier(
    var percentage: Float,
    var duration: Float
)

class MoveComponent(
    override val parent: Entity,

    val defaultSpeed: Float = 5f,
    val acceleration: Float = 0.02f,
    var currentSpeed: Float = 0f
) : DefaultMoveComponent(parent, defaultSpeed) {
    var movementPercentage: Float = 0.00f

    private var modifiers: MutableCollection<MoveModifier> = mutableListOf()

    var computedSpeed: Float = 0f
        get() = defaultSpeed * (1f + modifiers.map { it.percentage }.sum())

    override fun update(delta: Float) {
        getRequiredSibling<GeometricComponent>().let {
            movementPercentage = getOptionalSibling<LogicComponent>()
                ?.computeSpeedPercentage() ?: 1.0f
            val desiredSpeed = computedSpeed * movementPercentage
            if (currentSpeed < desiredSpeed) {
                currentSpeed += acceleration
            } else if (currentSpeed > desiredSpeed) {
                currentSpeed -= acceleration
            }

            speed = currentSpeed

            super.update(delta)
        }

        modifiers.forEach { it.duration -= delta }
        modifiers.removeAll { it.duration < 0f }
    }

    fun addModifier(percentage: Float, duration: Float) {
        modifiers.add(MoveModifier(percentage, duration))
    }

    override fun direction(): Float {
        return getRequiredSibling<GeometricComponent>().directionAngle
    }
}