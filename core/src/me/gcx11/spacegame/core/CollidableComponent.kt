package me.gcx11.spacegame.core

interface CollidableComponent : DisposableComponent {
    val collidedCollection: MutableCollection<CollidableComponent>

    fun addCollided(collidable: CollidableComponent) = collidedCollection.add(collidable)

    fun clearAllCollided() = collidedCollection.clear()

    fun allCollided(): Collection<CollidableComponent> = collidedCollection

    override fun dispose() = clearAllCollided()

    fun collidesWith(collidable: CollidableComponent): Boolean {
        val selfGeo = parent.getRequiredComponent<GeometricComponent>()
        val otherGeo = collidable
                .parent
                .getOptionalComponent<me.gcx11.spacegame.spaceship.GeometricComponent>()
        return otherGeo?.shape?.intersectsWith(selfGeo.shape) ?: false
    }
}
