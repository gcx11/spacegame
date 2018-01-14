package me.gcx11.spacegame.core

interface CollidableComponent : Component {
    fun collideWith(component: CollidableComponent): Boolean
}
