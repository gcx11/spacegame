package me.gcx11.spacegame.spaceship

abstract class LogicScript(
    val component: EnemyLogicComponent,

    val priority: Int = 0
) {
    abstract fun needActivation(): Boolean
    abstract fun update(delta: Float)
    abstract fun enable()
    abstract fun disable()
}