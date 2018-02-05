package me.gcx11.spacegame.spaceship

import me.gcx11.spacegame.core.Point
import me.gcx11.spacegame.core.inRange
import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

class WanderScript(
    component: EnemyLogicComponent,
    priority: Int
) : LogicScript(component, priority) {
    var destination: Point? = null

    private val rnd = Random()

    override fun needActivation(): Boolean {
        return true
    }

    override fun update(delta: Float) {
        val shipGeo = component.parent.getRequiredComponent<GeometricComponent>()
        val distance = hypot(
            abs(shipGeo.x - destination!!.x),
            abs(shipGeo.y - destination!!.y)
        )

        if (distance < 10f) destination = newRandomDestination()
        component.direction = atan2(destination!!.x - shipGeo.x, destination!!.y - shipGeo.y)
        component.canFire = false
        component.canSpeedUp = false
        component.speedPercentage = 0.3f
    }

    override fun enable() {
        destination = newRandomDestination()
    }

    override fun disable() {
        destination = null
    }

    fun newRandomDestination(): Point {
        val shipPosition = component.parent.getRequiredComponent<GeometricComponent>()

        return Point(
            shipPosition.x + rnd.inRange(-100..100),
            shipPosition.y + rnd.inRange(-100..100)
        )
    }
}