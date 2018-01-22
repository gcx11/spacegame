package me.gcx11.spacegame.core

class Entity(
    val id: Int,
    val components: MutableList<Component> = mutableListOf()
) {
    override fun toString(): String {
        val description = "Entity ${id} with " + buildString {
            components.forEach {
                append("\n\t")
                append(it.toString())
            }
        }
        return description
    }

    fun addComponent(component: Component) {
        components.add(component)
    }

    inline fun <reified T : Component> hasComponent(): Boolean {
        return components.any { it is T }
    }

    inline fun <reified T : Component> getRequiredComponent(): T {
        return components.first { it is T } as T
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

    companion object {
        var currentId = 0

        fun new(): Entity {
            return Entity(currentId++)
        }
    }
}