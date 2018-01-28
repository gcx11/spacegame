package me.gcx11.spacegame.core

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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
