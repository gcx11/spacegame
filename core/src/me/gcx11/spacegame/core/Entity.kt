package me.gcx11.spacegame.core

import me.gcx11.spacegame.core.components.Component

/**
 * Entity - a basic game entity
 *
 * This class is intented to be used as a parent class
 * and manager of stacked components [Component].
 *
 * @param id of current entity
 * @param components creates mutable collection of objects of [Component]
 * @sample [me.gcx11.spacegame.spaceship.RenderableComponent]
 */
class Entity(
    val id: Int,
    val components: MutableList<Component> = mutableListOf()
) {

    fun addComponent(component: Component) {
        components.add(component)
    }

    inline fun <reified T : Component> hasComponent(): Boolean {
        return components.any { it is T }
    }

    inline fun <reified T : Component> getRequiredComponent(): T {
        return getOptionalComponent()
            ?: throw Exception("Component of type ${T::class.java.name} not found")
    }

    inline fun <reified T : Component> getOptionalComponent(): T? {
        return components.firstOrNull { it is T } as T?
    }

    inline fun <reified T : Component> getAllComponents(): List<T> {
        return components.mapNotNull { it as? T }
    }

    inline fun <reified T : Component> removeAllComponents() {
        components.removeAll { it is T }
    }

    override fun toString(): String {
        val description = "Entity ${id} with " + buildString {
            components.forEach {
                append("\n\t")
                append(it.toString())
            }
        }
        return description
    }

    companion object {
        var currentId = 0

        fun new(): Entity {
            return Entity(currentId++)
        }
    }
}