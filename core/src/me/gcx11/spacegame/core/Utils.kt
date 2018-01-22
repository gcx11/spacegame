package me.gcx11.spacegame.core

import kotlin.math.PI

const val PI_FLOAT = PI.toFloat()

fun Float.radiansToDegrees(): Float {
    return 180f * (this / PI_FLOAT)
}

fun Float.toScreenAngle(): Float {
    return this.radiansToDegrees() + 90f
}