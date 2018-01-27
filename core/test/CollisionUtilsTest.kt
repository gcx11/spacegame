
import me.gcx11.spacegame.core.Complex
import me.gcx11.spacegame.core.Line
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Shape
import me.gcx11.spacegame.core.Triangle
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CollisionUtilsTest {

    private fun checkIntersectionBothWays(first: Shape, second: Shape): Boolean {
        return first.intersectsWith(second) && second.intersectsWith(first)
    }

    @Test
    fun pointWithPoint() {
        val first = Point(0f, 0f)
        val second = Point(0f, 0f)
        val third = Point(1f, 1f)

        assertTrue(checkIntersectionBothWays(first, second))
        assertFalse(checkIntersectionBothWays(first, third))
    }

    @Test
    fun pointWithLine() {
        val line = Line(Point(0f, 0f), Point(1f, 1f))
        val middle = Point(0.5f, 0.5f)
        val start = Point(0f, 0f)
        val end = Point(1f, 1f)
        val afterLine = Point(2f, 2f)
        val outCompletely = Point(50f, -3f)

        assertTrue(checkIntersectionBothWays(line, start))
        assertTrue(checkIntersectionBothWays(line, end))
        assertTrue(checkIntersectionBothWays(line, middle))
        assertFalse(checkIntersectionBothWays(line, afterLine))
        assertFalse(checkIntersectionBothWays(line, outCompletely))
    }

    @Test
    fun pointWithTriangle() {
        val origin = Point(0f, 0f)
        val pointInside = Point(0.3f, 0.2f)
        val hypotenuseMiddle = Point(0.5f, 0.5f)
        val other = Point(2f, 2f)
        val triangle = Triangle(Point(0f, 0f), Point(1f, 0f), Point(0f, 1f))

        assertTrue(checkIntersectionBothWays(triangle, pointInside))
        assertTrue(checkIntersectionBothWays(triangle, origin))
        assertTrue(checkIntersectionBothWays(triangle, hypotenuseMiddle))
        assertFalse(checkIntersectionBothWays(triangle, other))
    }

    @Test
    fun lineWithLine() {
        val first = Line(Point(0f, 0f), Point(5f, 5f))
        val second = Line(Point(3f, 0f), Point(0f, 3f))
        val third = Line(Point(0f, 0f), Point(-5f, -5f))

        assertTrue(checkIntersectionBothWays(first, second))
        assertTrue(checkIntersectionBothWays(first, third))
        assertFalse(checkIntersectionBothWays(second, third))
    }

    @Test
    fun triangleWithLine() {
        val triangle = Triangle(Point(0f, 0f), Point(1f, 0f), Point(0f, 1f))
        val sameLine = Line(Point(0f, 0f), Point(1f, 0f))
        val perpendicularLine = Line(Point(0.5f, -0.2f), Point(0.5f, 0.2f))
        val otherLine = Line(Point(2f, 0f), Point(0f, 2f))

        assertTrue(checkIntersectionBothWays(triangle, sameLine))
        assertTrue(checkIntersectionBothWays(triangle, perpendicularLine))
        assertFalse(checkIntersectionBothWays(triangle, otherLine))
    }

    @Test
    fun triangleWithTriangle() {
        val first = Triangle(Point(0f, 0f), Point(1f, 0f), Point(0f, 1f))
        val second = Triangle(
            Point(0.25f, 0.25f), Point(1.25f, 0.25f), Point(0.25f, 1.25f)
        )
        val third = Triangle(
            Point(-0.25f, -0.25f), Point(-1.25f, -0.25f), Point(-0.25f, -1.25f)
        )

        assertTrue(checkIntersectionBothWays(first, second))
        assertFalse(checkIntersectionBothWays(first, third))
    }

    @Test
    fun complexShapes() {
        val insidePoint = Point(5f, 5f)
        val outsidePoint = Point(10f, 10f)

        val lowerTriangle = Triangle(Point(0f, 0f), Point(0f, 9f), Point(9f, 0f))
        val upperTriangle = Triangle(Point(0f, 9f), Point(9f, 0f), Point(9f, 9f))

        val square = Complex(
            setOf(lowerTriangle, upperTriangle)
        )

        assertTrue(checkIntersectionBothWays(square, insidePoint))
        assertFalse(checkIntersectionBothWays(square, outsidePoint))
        assertTrue(checkIntersectionBothWays(square, lowerTriangle))
        assertTrue(checkIntersectionBothWays(square, upperTriangle))
    }
}