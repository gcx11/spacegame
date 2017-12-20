package me.gcx11.spacegame.spaceship

import com.badlogic.gdx.Gdx
import me.gcx11.spacegame.core.BehaviourComponent
import me.gcx11.spacegame.core.Entity

class RotateBehaviourComponent(
    override val parent: Entity,

    val rotateSpeed: Float = 5f
) : BehaviourComponent {
    val threshold = Math.PI.toFloat()/90f

    override fun update(delta: Float) {
        parent.getComponent<GeometricComponent>()?.let {
            val mouseX = Gdx.input.x
            val mouseY = Gdx.input.y

            val mouseAngle = Math.atan2((mouseY - it.y).toDouble(),
                                        (mouseX - it.x).toDouble())

            if (it.directionAngle < mouseAngle ) {
                val diff = mouseAngle - it.directionAngle

                if (diff < threshold) {
                    it.directionAngle = mouseAngle.toFloat()
                } else if (diff > Math.PI.toFloat()) {
                    it.directionAngle -= rotateSpeed * delta
                } else {
                    it.directionAngle += rotateSpeed * delta
                }
            } else if (it.directionAngle > mouseAngle) {
                val diff = it.directionAngle - mouseAngle

                if (diff < threshold) {
                    it.directionAngle = mouseAngle.toFloat()
                } else if (diff > Math.PI.toFloat()) {
                    it.directionAngle += rotateSpeed * delta
                } else {
                    it.directionAngle -= rotateSpeed * delta
                }
            }

            if (it.directionAngle < -Math.PI) {
                it.directionAngle += 2*Math.PI.toFloat()
            }
            else if (it.directionAngle > Math.PI) {
                it.directionAngle -= 2*Math.PI.toFloat()
            }
            //it.directionAngle = mouseAngle.toFloat()
        }
    }
}