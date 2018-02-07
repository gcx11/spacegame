package me.gcx11.spacegame.core.utils

import java.util.*
import kotlin.math.PI

const val PI_FLOAT = PI.toFloat()

fun Float.radiansToDegrees(): Float {
    return 180f * (this / PI_FLOAT)
}

fun Float.toScreenAngle(): Float {
    return this.radiansToDegrees() + 90f
}

fun Random.inRange(range: IntRange): Int {
    return range.first + nextInt(range.last - range.first)
}