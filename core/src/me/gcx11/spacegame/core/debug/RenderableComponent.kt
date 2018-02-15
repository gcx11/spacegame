package me.gcx11.spacegame.core.debug

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.components.DisposableComponent
import me.gcx11.spacegame.core.components.RenderableComponent
import me.gcx11.spacegame.core.components.getRequiredSibling

class RenderableComponent(
    override val parent: Entity
) : RenderableComponent, DisposableComponent {
    val batch = SpriteBatch()
    val font = BitmapFont()

    override fun draw() {
        val fps = getRequiredSibling<BehaviourComponent>().currentFPS

        batch.begin()
        batch.color = Color.WHITE
        font.draw(batch, "FPS: $fps", 0f, 20f)
        batch.end()
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }
}