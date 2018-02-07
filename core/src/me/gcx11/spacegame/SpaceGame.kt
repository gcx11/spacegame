package me.gcx11.spacegame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.camera.CameraSpawner
import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.components.CameraComponent
import me.gcx11.spacegame.core.components.CollidableComponent
import me.gcx11.spacegame.core.components.DisposableComponent
import me.gcx11.spacegame.core.components.RenderableComponent
import me.gcx11.spacegame.meteor.MeteorSpawner
import me.gcx11.spacegame.spaceship.SpaceshipSpawner
import me.gcx11.spacegame.star.StarSpawner
import java.util.*

class SpaceGame : ApplicationAdapter() {
    companion object {
        private val entities: MutableList<Entity> = mutableListOf()
        private val entitiesToAdd: MutableList<Entity> = mutableListOf()
        private val entitiesToDelete: MutableList<Entity> = mutableListOf()

        val entitiesReadOnly: List<Entity> = entities
        val camera = CameraSpawner.createLibGdxCamera()

        fun addLater(entity: Entity) {
            entitiesToAdd.add(entity)
        }

        fun deleteLater(entity: Entity) {
            entitiesToDelete.add(entity)
        }
    }

    override fun create() {
        camera.getRequiredComponent<CameraComponent>().setDimensions(
                Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()
            )

        val player = SpaceshipSpawner.createPlayer(0f, 0f)

        entities.add(player)
        entities.add(SpaceshipSpawner.createEnemy(500f, 500f))
        entities.add(MeteorSpawner.createMeteor(300f, 300f))

        val random = Random()
        for (i in 0 until 100) {
            entities.add(
                StarSpawner.createStar(1000 * random.nextFloat(), 1000 * random.nextFloat())
            )
        }

        camera.getRequiredComponent<CameraComponent>().follow(player)
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime

        for (ent in entities) {
            ent.getAllComponents<BehaviourComponent>().forEach { it.update(delta) }
        }

        camera.getRequiredComponent<CameraComponent>().update(delta)

        val collidables = entities.mapNotNull { it.getOptionalComponent<CollidableComponent>() }
            .onEach { it.clearAllCollided() }

        for (i in 0 until collidables.size) {
            for (j in i + 1 until collidables.size) {
                if (collidables[i].collidesWith(collidables[j]) ||
                    collidables[j].collidesWith(collidables[i])) {
                    collidables[i].addCollided(collidables[j])
                    collidables[j].addCollided(collidables[i])
                }
            }
        }

        entities.removeAll(entitiesToDelete)
        entitiesToDelete.flatMap { it.getAllComponents<DisposableComponent>() }.forEach { it.dispose() }
        entitiesToDelete.clear()
        entities.addAll(entitiesToAdd)
        entitiesToAdd.clear()

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        for (ent in entities) {
            ent.getAllComponents<RenderableComponent>()
                .filter {
                    camera.getRequiredComponent<CameraComponent>().isVisible(it.parent)
                }
                .forEach { it.draw() }
        }
    }

    override fun dispose() {
    }
}