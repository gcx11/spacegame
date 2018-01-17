package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.math.Vector2
import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.isPointInsideTriangle

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
            val points = arrayOf(
                Vector2(otherGeo.noseY, otherGeo.noseY),
                Vector2(otherGeo.leftWingX, otherGeo.leftWingY),
                Vector2(otherGeo.rightWingX, otherGeo.rightWingY)
            )

            val leftPart = arrayOf(
                Vector2(selfGeo.noseX, selfGeo.noseY),
                Vector2(selfGeo.leftWingX, selfGeo.leftWingY),
                Vector2(selfGeo.x, selfGeo.y)
            )

            val rightPart = arrayOf(
                Vector2(selfGeo.noseX, selfGeo.noseY),
                Vector2(selfGeo.rightWingX, selfGeo.rightWingY),
                Vector2(selfGeo.x, selfGeo.y)
            )

            return points.any { p ->
                isPointInsideTriangle(p, leftPart[0], leftPart[1], leftPart[2]) ||
                    isPointInsideTriangle(p, rightPart[0], rightPart[1], rightPart[2])
            }
        }

        return false
    }
}