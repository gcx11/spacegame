package me.gcx11.spacegame.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.components.CameraComponent
import me.gcx11.spacegame.core.components.CollidableComponent
import me.gcx11.spacegame.core.components.RenderableComponent

class World {
    private val entities: MutableList<Entity> = mutableListOf()

    fun update(delta: Float) {
        for (ent in entities) {
            ent.getAllComponents<BehaviourComponent>().forEach { it.update(delta) }
        }

        SpaceGame.camera.getRequiredComponent<CameraComponent>().update(delta)

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
    }

    fun draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        for (ent in entities) {
            ent.getAllComponents<RenderableComponent>()
                .filter {
                    SpaceGame.camera.getRequiredComponent<CameraComponent>().isVisible(it.parent)
                }
                .forEach { it.draw() }
        }
    }

    fun add(entity: Entity) {
        entities.add(entity)
    }

    fun remove(entity: Entity) {
        entities.remove(entity)
    }

    fun allEntities(): List<Entity> = entities
}