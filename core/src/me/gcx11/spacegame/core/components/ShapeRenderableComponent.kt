package me.gcx11.spacegame.core.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.Composed
import me.gcx11.spacegame.core.ComposedFromTwo
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Line
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.Shape
import me.gcx11.spacegame.core.Triangle
import me.gcx11.spacegame.core.utils.use

class ShapeRenderableComponent(
    override val parent: Entity,

    val color: Color = Color.WHITE,
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
) : RenderableComponent, DisposableComponent {

    override fun draw() {
        getOptionalSibling<GeometricComponent>()?.let {
            shapeRenderer.use(color) {
                drawShape(it.shape)
            }
        }
    }

    private fun drawShape(shape: Shape) {
        with(shapeRenderer) {
            return when (shape) {
                is Point -> circle(shape.x, shape.y, 1f)
                is Line -> rectLine(
                    shape.first.x, shape.first.y, shape.second.x, shape.second.y, 1f
                )
                is Triangle -> triangle(
                    shape.first.x, shape.first.y,
                    shape.second.x, shape.second.y,
                    shape.third.x, shape.third.y
                )
                is ComposedFromTwo -> {
                    drawShape(shape.first)
                    drawShape(shape.second)
                }
                is Composed -> shape.subShapes.forEach {
                    drawShape(it)
                }
            }
        }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}