package me.gcx11.spacegame.core.debug

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.BehaviourComponent

class BehaviourComponent(
    override val parent: Entity
) : BehaviourComponent {

    var currentFPS = 0

    private var newFPS = 0
    private var time = 0f

    override fun update(delta: Float) {
        newFPS += 1
        if (time > 1f) {
            currentFPS = newFPS
            newFPS = 0
            time = 0f
        }
        time += delta
    }
}