package me.gcx11.spacegame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.spaceship.GeometricComponent
import me.gcx11.spacegame.spaceship.PlayerLogicComponent
import me.gcx11.spacegame.spaceship.SpaceshipSpawner

class SpaceGame : ApplicationAdapter() {
    companion object {
        private var entities: MutableList<Entity> = mutableListOf()
        private var entitiesToAdd: MutableList<Entity> = mutableListOf()
        private var entitiesToDelete: MutableList<Entity> = mutableListOf()

        val entitiesReadOnly: List<Entity> = entities
        val camera = OrthographicCamera()

        fun addLater(entity: Entity) {
            entitiesToAdd.add(entity)
        }

        fun deleteLater(entity: Entity) {
            entitiesToDelete.add(entity)
        }
    }

    override fun create() {
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        entities.add(SpaceshipSpawner.createPlayer(0f, 0f))
        entities.add(SpaceshipSpawner.createEnemy(500f, 500f))
    }

    override fun render() {
        for (ent in entities) {
            val delta = Gdx.graphics.deltaTime
            ent.getAllComponents<BehaviourComponent>().forEach { it.update(delta) }
        }

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
        entities.firstOrNull { it.hasComponent<PlayerLogicComponent>() }
            ?.getOptionalComponent<GeometricComponent>()?.let {
                camera.position.set(it.x, it.y, 0f)
                camera.update()
            }

        for (ent in entities) {
            ent.getAllComponents<RenderableComponent>().forEach { it.draw() }
        }
    }

    override fun dispose() {
    }
}