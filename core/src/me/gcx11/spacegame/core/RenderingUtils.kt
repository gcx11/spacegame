package me.gcx11.spacegame.core

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import me.gcx11.spacegame.SpaceGame

inline fun ShapeRenderer.use(
    color: Color,
    projectionMatrix: Matrix4 = SpaceGame.camera.combined,
    shapeType: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line,
    block: ShapeRenderer.() -> Unit
) {
    this.projectionMatrix = projectionMatrix.cpy()
    this.begin(shapeType)
    this.color = color
    block(this)
    this.end()
}