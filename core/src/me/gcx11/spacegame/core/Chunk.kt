package me.gcx11.spacegame.core

import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.components.GeometricComponent
import me.gcx11.spacegame.core.components.MoveComponent
import me.gcx11.spacegame.core.events.MoveEvent

class Chunk(
    val world: World,
    val x: Int,
    val y: Int
) {

    private val entities: MutableList<Entity> = mutableListOf()

    val isActive: Boolean
        get() {
            return SpaceGame.camera.getRequiredComponent<GeometricComponent>().shape.intersectsWith(
                ComposedFromTwo(
                    Triangle(
                        Point((x * width).toFloat(), (y * height).toFloat()),
                        Point(((x + 1) * width).toFloat(), (y * height).toFloat()),
                        Point((x * width).toFloat(), ((y + 1) * height).toFloat())
                    ),
                    Triangle(
                        Point(((x + 1) * width).toFloat(), (y * height).toFloat()),
                        Point((x * width).toFloat(), ((y + 1) * height).toFloat()),
                        Point(((x + 1) * width).toFloat(), ((y + 1) * height).toFloat())
                    )
                )
            )
        }

    companion object {
        val width: Int = 200
        val height: Int = 200
    }

    fun add(entity: Entity) {
        entity.getOptionalComponent<MoveComponent>()?.let {
                it.eventHandler += this::onEntityMove
            }
        entities.add(entity)
    }

    fun remove(entity: Entity) {
        if (!entities.remove(entity)) {
            throw Exception("Entity ${entity.id} not found in [$x, $y]")
        }
    }

    fun getAllEntities(): List<Entity> {
        return entities
    }

    fun onEntityMove(event: MoveEvent) {
        val entity = event.entity

        if (!hasInside(entity)) {
            entity.getOptionalComponent<MoveComponent>()?.let {
                    it.eventHandler -= this::onEntityMove
                }
            remove(entity)

            world.add(entity)
        }
    }

    fun hasInside(entity: Entity): Boolean {
        val entX = (entity.getRequiredComponent<GeometricComponent>().x / Chunk.width).toInt()
        val entY = (entity.getRequiredComponent<GeometricComponent>().y / Chunk.height).toInt()

        return x == entX && y == entY
    }
}