package me.gcx11.spacegame.core.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import me.gcx11.spacegame.SpaceGame
import me.gcx11.spacegame.core.camera.CameraComponent


inline fun ShapeRenderer.use(
    color: Color,
    shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Filled,
    projectionMatrix: Matrix4 =
    SpaceGame.camera.getRequiredComponent<CameraComponent>().internalCamera.combined,
    block: ShapeRenderer.() -> Unit
) {
    this.projectionMatrix = projectionMatrix
    this.begin(shapeType)
    this.color = color
    block(this)
    this.end()
}