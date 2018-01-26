package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.gcx11.spacegame.core.DisposableComponent
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.RenderableComponent
import me.gcx11.spacegame.core.use

class RenderableComponent(
    override val parent: Entity,

    val shapeRenderer: ShapeRenderer = ShapeRenderer(),
    val color: Color = Color.RED
) : RenderableComponent, DisposableComponent {

    override fun draw() {
        parent.getOptionalComponent<GeometricComponent>()
            ?.let {
                shapeRenderer.use(color) {
                    polygon(
                        arrayOf(
                            it.noseX, it.noseY,
                            it.leftWingX, it.leftWingY,
                            it.x, it.y,
                            it.rightWingX, it.rightWingY
                        ).toFloatArray()
                    )
                }
            }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}