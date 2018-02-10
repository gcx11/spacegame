package me.gcx11.spacegame.core.components

import me.gcx11.spacegame.core.Entity

interface Component {
    val parent: Entity
}

inline fun <reified T : Component> Component.getRequiredSibling(): T {
    return parent.getRequiredComponent()
}

inline fun <reified T : Component> Component.getOptionalSibling(): T? {
    return parent.getOptionalComponent()
}