package me.gcx11.spacegame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent

class SpaceGame : ApplicationAdapter() {
    companion object {
        private var entities: MutableList<Entity> = mutableListOf()
        private var entitiesToAdd: MutableList<Entity> = mutableListOf()
        private var entitiesToDelete: MutableList<Entity> = mutableListOf()

        fun addLater(entity: Entity) {
            entitiesToAdd.add(entity)
        }

        fun deleteLater(entity: Entity) {
            entitiesToDelete.add(entity)
        }
    }

    override fun create() {
        entities.add(Entity.new().also {
            it.addComponent(me.gcx11.spacegame.spaceship.GeometricComponent(
                    it, 300f, 200f, 20f, 5f, 15f))
            it.addComponent(me.gcx11.spacegame.spaceship.RenderableComponent(it))
            it.addComponent(me.gcx11.spacegame.spaceship.RotateBehaviourComponent(it))
            it.addComponent(me.gcx11.spacegame.spaceship.MoveBehaviourComponent(it))
            it.addComponent(me.gcx11.spacegame.spaceship.FireBehaviourComponent(it))
        })
    }

    override fun render() {
        for (ent in entities) {
            val delta = Gdx.graphics.deltaTime
            ent.getAllComponents<BehaviourComponent>().forEach { it.update(delta) }
        }

        entities.removeAll(entitiesToDelete)
        entitiesToDelete.flatMap { it.getAllComponents<DisposableComponent>() }
                .forEach { it.dispose() }
        entitiesToDelete.clear()
        entities.addAll(entitiesToAdd)
        entitiesToAdd.clear()

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        for (ent in entities) {
            ent.getAllComponents<RenderableComponent>().forEach { it.draw() }
        }
    }

    override fun dispose() {
    }
}