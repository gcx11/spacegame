package me.gcx11.spacegame.core.camera

import me.gcx11.spacegame.core.Entity

object CameraSpawner {

    fun createLibGdxCamera(): Entity {
        return Entity.new().apply {
            addComponent(me.gcx11.spacegame.core.camera.GeometryComponent(this))
            addComponent(me.gcx11.spacegame.core.camera.CameraComponent(this))
        }
    }
}