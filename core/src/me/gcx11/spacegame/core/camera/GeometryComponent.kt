package me.gcx11.spacegame.core.camera

import com.badlogic.gdx.Gdx
import me.gcx11.spacegame.core.ComposedFromTwo
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Reusable
import me.gcx11.spacegame.core.Triangle
import me.gcx11.spacegame.core.components.GeometricComponent
import me.gcx11.spacegame.core.components.getRequiredSibling

class GeometryComponent(
    override val parent: Entity
) : GeometricComponent {
    
    override var x: Float = 0f
        get() = getRequiredSibling<CameraComponent>().x
    override var y: Float = 0f
        get() = getRequiredSibling<CameraComponent>().y

    val leftUpper by Reusable(Point.default) {
        x = this@GeometryComponent.x - Gdx.graphics.width / 2f
        y = this@GeometryComponent.y - Gdx.graphics.height / 2f
    }

    val rightUpper by Reusable(Point.default) {
        x = this@GeometryComponent.x + Gdx.graphics.width / 2f
        y = this@GeometryComponent.y - Gdx.graphics.height / 2f
    }

    val leftLower by Reusable(Point.default) {
        x = this@GeometryComponent.x - Gdx.graphics.width / 2f
        y = this@GeometryComponent.y + Gdx.graphics.height / 2f
    }

    val rightLower by Reusable(Point.default) {
        x = this@GeometryComponent.x + Gdx.graphics.width / 2f
        y = this@GeometryComponent.y + Gdx.graphics.height / 2f
    }

    val leftTriangle by Reusable(Triangle.default) {
        first = leftUpper
        second = rightUpper
        third = leftLower
    }

    val rightTriangle by Reusable(Triangle.default) {
        first = leftLower
        second = rightUpper
        third = rightLower
    }

    override val shape by Reusable(ComposedFromTwo.default) {
        first = leftTriangle
        second = rightTriangle
    }
}