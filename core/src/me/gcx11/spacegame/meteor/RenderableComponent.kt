package me.gcx11.spacegame.meteor

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.core.Triangle
import me.gcx11.spacegame.core.use

class RenderableComponent(
    override val parent: Entity,

    val color: Color = Color.BROWN,
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
) : RenderableComponent {
    override fun draw() {
        parent.getOptionalComponent<GeometricComponent>()
            ?.let {
                shapeRenderer.use(color, shapeType = ShapeRenderer.ShapeType.Filled) {
                    it.shape.subShapes.filterIsInstance<Triangle>().forEach {
                            triangle(
                                it.first.x, it.first.y,
                                it.second.x, it.second.y,
                                it.third.x, it.third.y
                            )
                        }

                    /*for (i in 0 until it.points.size) {
                        line(it.points[i].vector, it.points[(i + 1) % it.points.size].vector)
                    }*/
                }
            }
    }
}