package me.gcx11.spacegame.core

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Reusable - a delegated property
 *
 * This class is intented to be used as delegated property.
 * Its main purpose is to avoid creating unnecessary instances;
 * and thus prevent lagging and excessive memory usage.
 *
 * @param T the type of a class owning delegated property.
 * @param func block function updating a instance
 * @param default the default value for a instance
 * @sample [me.gcx11.spacegame.spaceship.GeometricComponent.center]
 */
class Reusable<T>(
    val default: T,
    val func: T.() -> Unit
) : ReadOnlyProperty<Any, T> {
    private var instance: T = default

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        func(instance)
        return instance
    }
}
