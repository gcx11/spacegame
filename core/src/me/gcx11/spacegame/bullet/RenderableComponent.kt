package me.gcx11.spacegame.bullet

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DisposableComponent
import me.gcx11.spacegame.core.components.RenderableComponent
import me.gcx11.spacegame.core.components.getOptionalSibling
import me.gcx11.spacegame.core.utils.use

class RenderableComponent(
    override val parent: Entity,

    val shapeRenderer: ShapeRenderer = ShapeRenderer(),
    val color: Color = Color.FIREBRICK
) : RenderableComponent, DisposableComponent {
    override fun draw() {
        getOptionalSibling<GeometricComponent>()
            ?.let {
                shapeRenderer.use(color) {
                    rectLine(it.x, it.y, it.endX, it.endY, 1f)
                }
            }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}