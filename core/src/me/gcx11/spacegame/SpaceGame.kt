package me.gcx11.spacegame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.ComposedFromTwo
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.core.Reusable
import me.gcx11.spacegame.core.Triangle
import me.gcx11.spacegame.spaceship.GeometricComponent
import me.gcx11.spacegame.spaceship.PlayerLogicComponent
import me.gcx11.spacegame.spaceship.SpaceshipSpawner
import me.gcx11.spacegame.star.StarSpawner
import java.util.*

class SpaceGame : ApplicationAdapter() {
    companion object {
        private val entities: MutableList<Entity> = mutableListOf()
        private val entitiesToAdd: MutableList<Entity> = mutableListOf()
        private val entitiesToDelete: MutableList<Entity> = mutableListOf()

        val entitiesReadOnly: List<Entity> = entities
        val camera = OrthographicCamera()

        fun addLater(entity: Entity) {
            entitiesToAdd.add(entity)
        }

        fun deleteLater(entity: Entity) {
            entitiesToDelete.add(entity)
        }
    }

    val leftUpper by Reusable(Point.default) {
        x = camera.position.x - Gdx.graphics.width / 2f
        y = camera.position.y - Gdx.graphics.height / 2f
    }

    val rightUpper by Reusable(Point.default) {
        x = camera.position.x + Gdx.graphics.width / 2f
        y = camera.position.y - Gdx.graphics.height / 2f
    }

    val leftLower by Reusable(Point.default) {
        x = camera.position.x - Gdx.graphics.width / 2f
        y = camera.position.y + Gdx.graphics.height / 2f
    }

    val rightLower by Reusable(Point.default) {
        x = camera.position.x + Gdx.graphics.width / 2f
        y = camera.position.y + Gdx.graphics.height / 2f
    }

    val leftTriangle by Reusable(Triangle.default) {
        first = leftUpper
        second = rightUpper
        third = leftLower
    }

    val rightTriangle by Reusable(Triangle.default) {
        first = leftLower
        second = rightUpper
        third = rightLower
    }

    val cameraShape by Reusable(ComposedFromTwo.default) {
        first = leftTriangle
        second = rightTriangle
    }

    override fun create() {
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        entities.add(SpaceshipSpawner.createPlayer(0f, 0f))
        entities.add(SpaceshipSpawner.createEnemy(500f, 500f))

        val random = Random()
        for (i in 0 until 100) {
            entities.add(
                StarSpawner.createStar(1000 * random.nextFloat(), 1000 * random.nextFloat())
            )
        }
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
                camera.update(true)
            }

        for (ent in entities) {
            ent.getAllComponents<RenderableComponent>()
                .filter {
                    it.parent.getOptionalComponent<me.gcx11.spacegame.core.GeometricComponent>()
                        ?.shape?.intersectsWith(cameraShape) ?: false
                }
                .forEach { it.draw() }
        }
    }

    override fun dispose() {
    }
}