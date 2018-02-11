package me.gcx11.spacegame.core

import com.badlogic.gdx.Gdx
import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.components.CameraComponent
import me.gcx11.spacegame.core.components.DisposableComponent
import me.gcx11.spacegame.core.debug.DebugSpawner
import me.gcx11.spacegame.meteor.MeteorSpawner
import me.gcx11.spacegame.spaceship.SpaceshipSpawner
import me.gcx11.spacegame.star.StarSpawner
import java.util.*

class GameScene : Scene {

    private val entitiesToAdd: MutableList<Entity> = mutableListOf()
    private val entitiesToDelete: MutableList<Entity> = mutableListOf()

    private val world = World()

    val entitiesReadOnly: List<Entity> = world.allEntities()

    fun addLater(entity: Entity) {
        entitiesToAdd.add(entity)
    }

    fun deleteLater(entity: Entity) {
        entitiesToDelete.add(entity)
    }

    override fun setup() {
        SpaceGame.camera.getRequiredComponent<CameraComponent>().setDimensions(
                Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()
            )

        val player = SpaceshipSpawner.createPlayer(0f, 0f)

        world.add(player)
        world.add(SpaceshipSpawner.createEnemy(500f, 500f))
        world.add(MeteorSpawner.createMeteor(300f, 300f))

        val random = Random()
        for (i in 0 until 100) {
            world.add(
                StarSpawner.createStar(1000 * random.nextFloat(), 1000 * random.nextFloat())
            )
        }

        world.add(DebugSpawner.createDebug())

        SpaceGame.camera.getRequiredComponent<CameraComponent>().follow(player)
    }

    override fun update(delta: Float) {
        world.update(delta)

        entitiesToDelete.forEach { world.remove(it) }
        entitiesToDelete.flatMap { it.getAllComponents<DisposableComponent>() }.forEach { it.dispose() }
        entitiesToDelete.clear()
        entitiesToAdd.forEach { world.add(it) }
        entitiesToAdd.clear()
    }

    override fun draw() {
        world.draw()
    }
}