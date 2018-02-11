package me.gcx11.spacegame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import me.gcx11.spacegame.core.GameScene
import me.gcx11.spacegame.core.camera.CameraSpawner

class SpaceGame : ApplicationAdapter() {
    companion object {
        val camera = CameraSpawner.createLibGdxCamera()
        val scene = GameScene()
    }

    override fun create() {
        scene.setup()
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime
        scene.update(delta)

        scene.draw()
    }

    override fun dispose() {
    }
}