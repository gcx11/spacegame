
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Reusable
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReusablesTest {

    var testX = 0f
    var testY = 0f

    val point by Reusable(Point.default) {
        x = testX
        y = testY
    }

    @Test
    fun testReusablePoint() {
        assertTrue(point.x == 0f)
        assertTrue(point.y == 0f)
        testX = 1f
        testY = 2f
        assertTrue(point.x == 1f)
        assertTrue(point.y == 2f)
    }
}