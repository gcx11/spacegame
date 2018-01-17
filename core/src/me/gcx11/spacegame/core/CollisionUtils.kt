package me.gcx11.spacegame.core

import com.badlogic.gdx.math.Vector2

fun isPointInsideTriangle(p: Vector2, v1: Vector2, v2: Vector2, v3: Vector2): Boolean {
    // https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
    val t = p.x - v1.x
    val u = p.y - v1.y
    val v = ((v2.x - v1.x) * u - (v2.y - v1.y) * t) > 0

    if (((v3.x - v1.x) * u - (v3.y - v1.y) * t > 0) == v) {
        return false
    } else {
        return ((v3.x - v2.x) * (p.y - v2.y) - (v3.y - v2.y) * (p.x - v2.x) > 0) == v
    }
}