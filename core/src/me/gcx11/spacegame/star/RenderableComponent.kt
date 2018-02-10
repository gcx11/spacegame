package me.gcx11.spacegame.star

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.CollidableComponent
import me.gcx11.spacegame.core.components.RenderableComponent
import me.gcx11.spacegame.core.components.ShapeRenderableComponent
import me.gcx11.spacegame.core.components.getOptionalSibling

class RenderableComponent(
    override val parent: Entity,

    val color: Color = Color.WHITE,
    val shapeRenderer: ShapeRenderer = ShapeRenderer()
) : RenderableComponent {
    private val internalComponent = ShapeRenderableComponent(parent, color, shapeRenderer)

    override fun draw() {
        val visible = getOptionalSibling<CollidableComponent>()
            ?.allCollided()?.isEmpty() ?: true

        if (visible) {
            internalComponent.draw()
        }
    }
}