package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.core.use

class RenderableComponent(
    override val parent: Entity,

    val shapeRenderer: ShapeRenderer = ShapeRenderer(),
    val color: Color = Color.RED
) : RenderableComponent, DisposableComponent {

    override fun draw() {
        parent.getOptionalComponent<GeometricComponent>()
            ?.let {
                shapeRenderer.use(color, shapeType = ShapeRenderer.ShapeType.Filled) {
                    triangle(
                        it.leftPart.first.x, it.leftPart.first.y,
                        it.leftPart.second.x, it.leftPart.second.y,
                        it.leftPart.third.x, it.leftPart.third.y
                    )
                    triangle(
                        it.rightPart.first.x, it.rightPart.first.y,
                        it.rightPart.second.x, it.rightPart.second.y,
                        it.rightPart.third.x, it.rightPart.third.y
                    )
                }
            }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}