package me.gcx11.spacegame.core

class Entity(
    val id: Int,
    val components: MutableList<Component> = mutableListOf()
) {
    fun addComponent(component: Component) {
        components.add(component)
    }

    inline fun <reified T : Component> getComponent(): T? {
        return components.firstOrNull { it is T } as T?
    }

    inline fun <reified T : Component> getAllComponents(): List<T> {
        return components.mapNotNull { it as? T }
    }

    companion object {
        var currentId = 0

        fun new(): Entity {
            return Entity(currentId++)
        }
    }
}