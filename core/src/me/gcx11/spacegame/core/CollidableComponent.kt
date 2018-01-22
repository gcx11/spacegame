package me.gcx11.spacegame.core

interface CollidableComponent : DisposableComponent {
    val collidedCollection: MutableCollection<CollidableComponent>

    fun collidesWith(collidable: CollidableComponent): Boolean {
        val selfGeo = parent.getRequiredComponent<GeometricComponent>()
        val otherGeo = collidable.parent.getOptionalComponent<GeometricComponent>()
        return otherGeo?.shape?.intersectsWith(selfGeo.shape) ?: false
    }

    fun addCollided(collidable: CollidableComponent) = collidedCollection.add(collidable)

    fun clearAllCollided() = collidedCollection.clear()

    fun allCollided(): Collection<CollidableComponent> = collidedCollection

    override fun dispose() = clearAllCollided()
}
