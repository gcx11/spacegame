package me.gcx11.spacegame.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import me.gcx11.spacegame.SpaceGame

fun main (args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(SpaceGame(), config)
}