package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.math.Vector2
import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.Entity
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
            val triangle = Triangle(
                Vector2(selfGeo.noseY, selfGeo.noseY),
                Vector2(selfGeo.leftWingX, selfGeo.leftWingY),
                Vector2(selfGeo.rightWingX, selfGeo.rightWingY)
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

            return triangle.intersectsWith(otherLeftTriangle) ||
                triangle.intersectsWith(otherRightTriangle)
        }

        return false
    }
}