import com.badlogic.gdx.math.Vector2
import me.gcx11.spacegame.core.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CollisionUtilsTest {

    val vec00 = Vector2(0F, 0F)
    val vec11 = Vector2(1F, 1F)
    val vec22 = Vector2(2F, 2F)
    val vec99 = Vector2(9F, 9F)
    val vec09 = Vector2(0F, 9F)
    val vec90 = Vector2(9F, 0F)
    val vec_50_50 = Vector2(50F, 50F)

    val point00 = Point(vec00)
    val point11 = Point(vec11)
    val point99 = Point(vec99)
    val point09 = Point(vec09)
    val point90 = Point(vec90)
    val point_50_50 = Point(vec_50_50)

    val line00to11 = Line(vec00, vec11)
    val triangle00_09_90 = Triangle(vec00, vec09, vec90)
    val triangle99_09_90 = Triangle(vec99, vec09, vec90)

    // Rectangle of 00 to 99
    val complexRectangle_00_99 = Complex(setOf(triangle00_09_90))

    private fun checkIntersectionBothWays(first: Shape, second: Shape): Boolean {
        return first.intersectsWith(second) && second.intersectsWith(first)
    }

    @Test
    fun pointWithPoint() {
        val first = Point(Vector2(0f, 0f))
        val second = Point(Vector2(0f, 0f))
        val third = Point(Vector2(1f, 1f))

        assertTrue(checkIntersectionBothWays(first, second))
        assertFalse(checkIntersectionBothWays(first, third))
    }

    @Test
    fun pointWithLine() {
        val line = Line(Vector2(0f, 0f), Vector2(1f, 1f))
        val middle = Point(Vector2(0.5f, 0.5f))
        val start = Point(Vector2(0f, 0f))
        val end = Point(Vector2(1f, 1f))
        val afterLine = Point(Vector2(2f, 2f))
        val outCompletely = Point(Vector2(50f, -3f))

        assertTrue(checkIntersectionBothWays(line, start))
        assertTrue(checkIntersectionBothWays(line, end))
        assertTrue(checkIntersectionBothWays(line, middle))
        assertFalse(checkIntersectionBothWays(line, afterLine))
        assertFalse(checkIntersectionBothWays(line, outCompletely))
    }

    @Test
    fun pointWithTriangle() {
        val origin = Point(Vector2(0f, 0f))
        val pointInside = Point(Vector2(0.3f, 0.2f))
        val hypotenuseMiddle = Point(Vector2(0.5f, 0.5f))
        val other = Point(Vector2(2f, 2f))
        val triangle = Triangle(Vector2(0f, 0f), Vector2(1f, 0f), Vector2(0f, 1f))

        assertTrue(checkIntersectionBothWays(triangle, pointInside))
        assertTrue(checkIntersectionBothWays(triangle, origin))
        assertTrue(checkIntersectionBothWays(triangle, hypotenuseMiddle))
        assertFalse(checkIntersectionBothWays(triangle, other))
    }

    @Test
    fun lineWithLine() {
        val first = Line(Vector2(0f, 0f), Vector2(5f, 5f))
        val second = Line(Vector2(3f, 0f), Vector2(0f, 3f))
        val third = Line(Vector2(0f, 0f), Vector2(-5f, -5f))

        assertTrue(checkIntersectionBothWays(first, second))
        assertTrue(checkIntersectionBothWays(first, third))
        assertFalse(checkIntersectionBothWays(second, third))
    }

    @Test
    fun triangleWithLine() {
        val triangle = Triangle(Vector2(0f, 0f), Vector2(1f, 0f), Vector2(0f, 1f))
        val sameLine = Line(Vector2(0f, 0f), Vector2(1f, 0f))
        val perpendicularLine = Line(Vector2(0.5f, -0.2f), Vector2(0.5f, 0.2f))
        val otherLine = Line(Vector2(2f, 0f), Vector2(0f, 2f))

        assertTrue(checkIntersectionBothWays(triangle, sameLine))
        assertTrue(checkIntersectionBothWays(triangle, perpendicularLine))
        assertFalse(checkIntersectionBothWays(triangle, otherLine))
    }

    @Test
    fun triangleWithTriangle() {
        val first = Triangle(Vector2(0f, 0f), Vector2(1f, 0f), Vector2(0f, 1f))
        val second = Triangle(
                Vector2(0.25f, 0.25f), Vector2(1.25f, 0.25f), Vector2(0.25f, 1.25f)
        )
        val third = Triangle(
                Vector2(-0.25f, -0.25f), Vector2(-1.25f, -0.25f), Vector2(-0.25f, -1.25f)
        )

        assertTrue(checkIntersectionBothWays(first, second))
        assertFalse(checkIntersectionBothWays(first, third))
    }

    @Test
    fun complexShapes() {
        assertTrue(checkIntersectionBothWays(complexRectangle_00_99, point11))
        assertTrue(checkIntersectionBothWays(complexRectangle_00_99, complexRectangle_00_99))
        assertFalse(checkIntersectionBothWays(complexRectangle_00_99, point_50_50))
    }
}