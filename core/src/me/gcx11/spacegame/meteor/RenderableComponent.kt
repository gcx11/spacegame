package me.gcx11.spacegame.meteor

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Triangle
import me.gcx11.spacegame.core.components.RenderableComponent
import me.gcx11.spacegame.core.components.getOptionalSibling
import me.gcx11.spacegame.core.utils.use

class RenderableComponent(
    override val parent: Entity,

    val color: Color = Color.BROWN,
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
) : RenderableComponent {
    override fun draw() {
        getOptionalSibling<GeometricComponent>()
            ?.let {
                shapeRenderer.use(color, shapeType = ShapeRenderer.ShapeType.Filled) {
                    it.shape.subShapes.filterIsInstance<Triangle>().forEach {
                            triangle(
                                it.first.x, it.first.y,
                                it.second.x, it.second.y,
                                it.third.x, it.third.y
                            )
                        }
                }
            }
    }
}