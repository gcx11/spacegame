package me.gcx11.spacegame.core.camera

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import me.gcx11.spacegame.core.Entity
import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.components.CameraComponent
import me.gcx11.spacegame.core.components.GeometricComponent
import me.gcx11.spacegame.core.components.getRequiredSibling

class CameraComponent(
    override val parent: Entity
) : CameraComponent {
    val internalCamera = OrthographicCamera()
    private var target: Entity? = null

    override var x: Float = 0f
        get() = internalCamera.position.x
    override var y: Float = 0f
        get() = internalCamera.position.y

    override fun follow(entity: Entity) {
        target = entity
    }

    override fun setDimensions(width: Float, height: Float) {
        internalCamera.setToOrtho(false, width, height)
    }

    override fun update(delta: Float) {
        target?.getRequiredComponent<GeometricComponent>()?.let {
                internalCamera.position.set(it.x, it.y, 0f)
                internalCamera.update(true)
            }
    }

    override fun isVisible(entity: Entity): Boolean {
        val shape = getRequiredSibling<GeometricComponent>().shape

        return entity.getOptionalComponent<GeometricComponent>()
            ?.shape?.intersectsWith(shape) ?: true
    }

    override fun unproject(point: Point): Point {
        val vec = internalCamera.unproject(Vector3(point.x, point.y, 0f))
        return Point(vec.x, vec.y)
    }
}