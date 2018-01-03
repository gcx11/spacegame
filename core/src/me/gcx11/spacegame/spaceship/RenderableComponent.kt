package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.SpaceGame
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
        parent.getOptionalComponent<GeometricComponent>()?.let {

            shapeRenderer.projectionMatrix = SpaceGame.camera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.color = color

            shapeRenderer.identity()
            shapeRenderer.translate(it.x, it.y, 0f)
            shapeRenderer.rotate(0f, 0f, 1f, it.directionAngle.toScreenAngle())
            shapeRenderer.translate(-it.x, -it.y, 0f)
            shapeRenderer.polygon(arrayOf(
                    it.x, it.y - it.noseSize,
                    it.x - it.wingSize, it.y + it.noseSize + it.backSize,
                    it.x, it.y,
                    it.x + it.wingSize, it.y + it.noseSize + it.backSize)
                    .toFloatArray())
            shapeRenderer.end()
        }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}