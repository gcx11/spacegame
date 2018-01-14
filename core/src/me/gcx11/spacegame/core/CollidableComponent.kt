package me.gcx11.spacegame.core

interface CollidableComponent : Component {
    fun collidesWith(component: CollidableComponent): Boolean
}
