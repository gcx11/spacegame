import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.spaceship.SpaceshipSpawner.createMinimalPrototype
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SpaceShipCollisionTest {

    @Test
    fun samePosition() {
        val firstShip = createMinimalPrototype(10f, 10f)
            .getRequiredComponent<CollidableComponent>()
        val secondShip = createMinimalPrototype(10f, 10f)
            .getRequiredComponent<CollidableComponent>()

        assertTrue(firstShip.collidesWith(secondShip))
    }
}