package me.gcx11.spacegame.star

import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.Entity

class CollidableComponent(
    override val parent: Entity,
    override val collidedCollection: MutableCollection<CollidableComponent> = mutableListOf()
) : CollidableComponent