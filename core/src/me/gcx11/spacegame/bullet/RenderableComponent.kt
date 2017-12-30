package me.gcx11.spacegame.bullet

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.math.cos
import kotlin.math.sin
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent

class RenderableComponent(
    override val parent: Entity,

    val shapeRenderer: ShapeRenderer = ShapeRenderer(),
    val color: Color = Color.FIREBRICK
) : RenderableComponent, DisposableComponent {
    override fun draw() {
        parent.getComponent<GeometricComponent>()?.let {
            val endX = it.x + it.size * cos(it.directionAngle)
            val endY = Gdx.graphics.height - 1f - (it.y + it.size * sin(it.directionAngle))

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.color = color
            shapeRenderer.rectLine(it.x, Gdx.graphics.height - 1f - it.y, endX, endY, 2f)
            shapeRenderer.end()
        }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}