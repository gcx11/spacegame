package me.gcx11.spacegame.core.debug

import me.gcx11.spacegame.core.Entity

object DebugSpawner {

    fun createDebug(): Entity {
        return Entity.new().apply {
            addComponent(BehaviourComponent(this))
            addComponent(RenderableComponent(this))
        }
    }
}