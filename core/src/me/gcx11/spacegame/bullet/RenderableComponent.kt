package me.gcx11.spacegame.bullet

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.core.toScreenAngle
import me.gcx11.spacegame.core.use

class RenderableComponent(
    override val parent: Entity,

    val shapeRenderer: ShapeRenderer = ShapeRenderer(),
    val color: Color = Color.FIREBRICK
) : RenderableComponent, DisposableComponent {
    override fun draw() {
        parent.getOptionalComponent<GeometricComponent>()
            ?.let {
                shapeRenderer.use(color) {
                    identity()

                    color = Color.WHITE
                    rectLine(it.x, it.y, it.endX, it.endY, 1f)
                    color = this@RenderableComponent.color

                    translate(it.x, it.y, 0f)
                    rotate(0f, 0f, 1f, it.directionAngle.toScreenAngle())
                    translate(-it.x, -it.y, 0f)

                    rectLine(it.x, it.y, it.x, it.y - it.size, 1f)
                }
            }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}