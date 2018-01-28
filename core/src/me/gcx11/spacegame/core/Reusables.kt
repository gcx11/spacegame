package me.gcx11.spacegame.core

import com.badlogic.gdx.math.Vector2
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ReusablePoint(
    var func: Point.() -> Unit
) : ReadOnlyProperty<Any, Point> {
    private var point = Point.origin

    override fun getValue(thisRef: Any, property: KProperty<*>): Point {
        func(point)
        return point
    }
}

class ReusableLine(
    var func: Line.() -> Unit
) : ReadOnlyProperty<Any, Line> {
    private var line = Line(Point.origin, Point.origin)

    override fun getValue(thisRef: Any, property: KProperty<*>): Line {
        func(line)
        return line
    }
}

class ReusableTriangle(
    var func: Triangle.() -> Unit
) : ReadOnlyProperty<Any, Triangle> {
    private var triangle = Triangle(Point.origin, Point.origin, Point.origin)

    override fun getValue(thisRef: Any, property: KProperty<*>): Triangle {
        func(triangle)
        return triangle
    }
}

class ReusableVector(
    var func: Vector2.() -> Unit
) : ReadOnlyProperty<Any, Vector2> {
    private var vector = Vector2.Zero.cpy()

    override fun getValue(thisRef: Any, property: KProperty<*>): Vector2 {
        func(vector)
        return vector
    }
}
