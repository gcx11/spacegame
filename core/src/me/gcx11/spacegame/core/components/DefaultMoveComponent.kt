package me.gcx11.spacegame.core.components

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.events.EventHandler
import me.gcx11.spacegame.core.events.MoveEvent
import kotlin.math.cos
import kotlin.math.sin

abstract class DefaultMoveComponent(
    override val parent: Entity,
    override var speed: Float,

    override val eventHandler: EventHandler<MoveEvent> = EventHandler()
) : MoveComponent {

    override fun update(delta: Float) {
        getRequiredSibling<GeometricComponent>().let {
            it.x += speed * cos(direction())
            it.y += speed * sin(direction())

            eventHandler(MoveEvent(parent))
        }
    }

    abstract fun direction(): Float
}