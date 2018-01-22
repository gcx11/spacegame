package me.gcx11.spacegame.bullet

import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.Entity

class CollidableComponent(
    override val parent: Entity,
    override val collidedCollection: MutableList<CollidableComponent> = mutableListOf()
) : CollidableComponent