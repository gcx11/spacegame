package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.core.toScreenAngle

class RenderableComponent(
        override val parent: Entity,

        val shapeRenderer: ShapeRenderer = ShapeRenderer(),
        val color: Color = Color.RED
) : RenderableComponent, DisposableComponent {

    override fun draw() {
        parent.getComponent<GeometricComponent>()?.let {
            val newY = Gdx.graphics.height - 1f - it.y

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.color = color

            shapeRenderer.identity()
            shapeRenderer.translate(it.x, newY, 0f)
            shapeRenderer.rotate(0f, 0f, 1f, it.directionAngle.toScreenAngle())
            shapeRenderer.translate(-it.x, -newY, 0f)
            shapeRenderer.polygon(arrayOf(
                    it.x, newY + it.noseSize,
                    it.x - it.wingSize, newY - it.noseSize - it.backSize,
                    it.x, newY,
                    it.x + it.wingSize, newY - it.noseSize - it.backSize)
                    .toFloatArray())
            shapeRenderer.end()
        }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}