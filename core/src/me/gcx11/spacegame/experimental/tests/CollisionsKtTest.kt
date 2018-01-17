package me.gcx11.spacegame.experimental.tests

import me.gcx11.spacegame.experimental.combinationsOfTwo
import org.junit.jupiter.api.Test

internal class CollisionsKtTest {

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @Test
    fun combinationsOfTwoSimple() {
        val vals = combinationsOfTwo(setOf(1, 2, 2, 3)).toSet()
        val expectedResult = setOf(Pair(1, 2), Pair(1, 3), Pair(2, 3))
        assert(vals == expectedResult)
    }

    @Test
    fun sequenceOfCollidedPairsTest() {
    }
}