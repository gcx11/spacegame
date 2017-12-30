package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.Gdx
import kotlin.math.atan2
import kotlin.math.PI
import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.Entity

class RotateBehaviourComponent(
    override val parent: Entity,

    val rotateSpeed: Float = 5f
) : BehaviourComponent {
    val threshold = PI / 90f

    override fun update(delta: Float) {
        parent.getComponent<GeometricComponent>()?.let {
            val mouseX = Gdx.input.x
            val mouseY = Gdx.input.y

            val mouseAngle = atan2(mouseY - it.y, mouseX - it.x)

            if (it.directionAngle < mouseAngle ) {
                val diff = mouseAngle - it.directionAngle

                if (diff < threshold) {
                    it.directionAngle = mouseAngle
                } else if (diff > PI) {
                    it.directionAngle -= rotateSpeed * delta
                } else {
                    it.directionAngle += rotateSpeed * delta
                }
            } else if (it.directionAngle > mouseAngle) {
                val diff = it.directionAngle - mouseAngle

                if (diff < threshold) {
                    it.directionAngle = mouseAngle
                } else if (diff > PI) {
                    it.directionAngle += rotateSpeed * delta
                } else {
                    it.directionAngle -= rotateSpeed * delta
                }
            }

            if (it.directionAngle < -PI) {
                it.directionAngle += 2*PI.toFloat()
            } else if (it.directionAngle > PI) {
                it.directionAngle -= 2*PI.toFloat()
            }
            //it.directionAngle = mouseAngle.toFloat()
        }
    }
}