package me.gcx11.spacegame.star

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.CollidableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.core.use

class RenderableComponent(
    override val parent: Entity,

    val color: Color = Color.WHITE,
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
) : RenderableComponent {
    override fun draw() {
        val visible = parent.getOptionalComponent<CollidableComponent>()
            ?.allCollided()?.isEmpty() ?: true

        if (visible) {
            parent.getOptionalComponent<GeometricComponent>()
                ?.let {
                    shapeRenderer.use(color) {
                        circle(it.x, it.y, 1f)
                    }
                }
        }
    }
}