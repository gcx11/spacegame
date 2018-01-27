package me.gcx11.spacegame.core

import com.badlogic.gdx.math.Vector2
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ReusablePoint(
    var func: Point.() -> Unit
) : ReadWriteProperty<Any, Point> {
    private var point = Point(0f, 0f)

    override fun getValue(thisRef: Any, property: KProperty<*>): Point {
        func(point)
        return point
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Point) {
        point = value
    }
}

class ReusableLine(
    var func: Line.() -> Unit
) : ReadWriteProperty<Any, Line> {
    private var line = Line(Point(0f, 0f), Point(0f, 0f))

    override fun getValue(thisRef: Any, property: KProperty<*>): Line {
        func(line)
        return line
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Line) {
        line = value
    }
}

class ReusableTriangle(
    var func: Triangle.() -> Unit
) : ReadWriteProperty<Any, Triangle> {
    private var triangle = Triangle(Point(0f, 0f), Point(0f, 0f), Point(0f, 0f))

    override fun getValue(thisRef: Any, property: KProperty<*>): Triangle {
        func(triangle)
        return triangle
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Triangle) {
        triangle = value
    }
}

class ReusableVector(
    var func: Vector2.() -> Unit
) : ReadWriteProperty<Any, Vector2> {
    private var vector = Vector2(0f, 0f)

    override fun getValue(thisRef: Any, property: KProperty<*>): Vector2 {
        func(vector)
        return vector
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Vector2) {
        vector = value
    }
}
