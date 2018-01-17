package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.LogicComponent

abstract class LogicComponent(
    override val parent: Entity
) : LogicComponent {

    abstract fun computeDirection(): Float
    abstract fun canFire(): Boolean
    abstract fun canSpeedUp(): Boolean
}