package me.gcx11.spacegame.experimental

import me.gcx11.spacegame.core.CollidableComponent
import kotlin.coroutines.experimental.buildSequence

fun <T> combinationsOfTwo(xs: Set<T>): Sequence<Pair<T, T>> {
    return buildSequence {
        for (i in 1..xs.size)
            yieldAll(xs.zip(xs.drop(i)))
    }
}

/**
 * Generates sequence of uniqiue pairs,
 * which collides.
 *
 */
fun sequenceOfCollidedPairs(collidables: Set<CollidableComponent>): Sequence<Pair<CollidableComponent, CollidableComponent>> {
    return buildSequence {
        yieldAll(combinationsOfTwo(collidables).filter {
            it.first.collidesWith(it.second)
        })
    }
}