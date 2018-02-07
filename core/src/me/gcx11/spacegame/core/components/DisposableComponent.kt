package me.gcx11.spacegame.core.components

import me.gcx11.spacegame.core.components.Component

interface DisposableComponent : Component {
    fun dispose()
}