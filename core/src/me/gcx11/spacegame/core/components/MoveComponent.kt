package me.gcx11.spacegame.core.components

import me.gcx11.spacegame.core.events.EventHandler
import me.gcx11.spacegame.core.events.MoveEvent

interface MoveComponent : BehaviourComponent, DisposableComponent {
    var speed: Float
    val eventHandler: EventHandler<MoveEvent>

    override fun dispose() {
        eventHandler.removeAll()
    }
}