package me.gcx11.spacegame.bullet

import com.badlogic.gdx.math.Vector2
import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Line
import me.gcx11.spacegame.core.Triangle

class CollidableComponent(
    override val parent: Entity,
    override val collidedCollection: MutableList<CollidableComponent> = mutableListOf()
) : CollidableComponent {

    override fun collidesWith(collidable: CollidableComponent): Boolean {
        val selfGeo = parent.getRequiredComponent<GeometricComponent>()
        val otherGeo = collidable
            .parent
            .getOptionalComponent<me.gcx11.spacegame.spaceship.GeometricComponent>()

        if (otherGeo != null) {
            val line = Line(
                Vector2(selfGeo.x, selfGeo.y),
                Vector2(selfGeo.endX, selfGeo.endY)
            )

            val otherLeftTriangle = Triangle(
                Vector2(otherGeo.noseX, otherGeo.noseY),
                Vector2(otherGeo.leftWingX, otherGeo.leftWingY),
                Vector2(otherGeo.x, otherGeo.y)
            )

            val otherRightTriangle = Triangle(
                Vector2(otherGeo.noseX, otherGeo.noseY),
                Vector2(otherGeo.rightWingX, otherGeo.rightWingY),
                Vector2(otherGeo.x, otherGeo.y)
            )

            return line.intersectsWith(otherLeftTriangle) ||
                line.intersectsWith(otherRightTriangle)
        } else {
            return false
        }
    }
}