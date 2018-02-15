package me.gcx11.spacegame.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.components.BehaviourComponent
import me.gcx11.spacegame.core.components.CameraComponent
import me.gcx11.spacegame.core.components.CollidableComponent
import me.gcx11.spacegame.core.components.GeometricComponent
import me.gcx11.spacegame.core.components.RenderableComponent

class World {

    val chunks: MutableMap<Pair<Int, Int>, Chunk> = mutableMapOf()

    private val entitiesToAdd: MutableList<Entity> = mutableListOf()
    private val entitiesToRemove: MutableList<Entity> = mutableListOf()

    fun update(delta: Float) {
        entitiesToRemove.forEach { remove(it) }
        entitiesToRemove.clear()

        entitiesToAdd.forEach { add(it) }
        entitiesToAdd.clear()

        val entities = allEntities()

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
        val entities = allEntities()

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
        val x = (entity.getRequiredComponent<GeometricComponent>().x / Chunk.width).toInt()
        val y = (entity.getRequiredComponent<GeometricComponent>().y / Chunk.height).toInt()

        val chunk = chunks.getOrPut(Pair(x, y), { Chunk(this, x, y) })
        chunk.add(entity)
    }

    fun remove(entity: Entity) {
        val x = (entity.getRequiredComponent<GeometricComponent>().x / Chunk.width).toInt()
        val y = (entity.getRequiredComponent<GeometricComponent>().y / Chunk.height).toInt()

        val chunk = chunks[(Pair(x, y))]
            ?: throw Exception("Chunk [$x, $y] doesn't exists, when removing entity ${entity.id}")
        chunk.remove(entity)
    }

    fun allEntities(): List<Entity> = chunks.values.filter { it.isActive }.flatMap { it.getAllEntities() }
}