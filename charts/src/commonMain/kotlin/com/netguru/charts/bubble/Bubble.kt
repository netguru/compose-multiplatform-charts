package com.netguru.charts.bubble

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

data class Bubble(
    val name: String,
    val value: Float,
    val icon: ImageVector,
    val color: Color,
    var radius: Float = value
) {
    var position: Vector = Vector(0f, 0f)
    var velocity: Vector = Vector(
        Random.nextFloat() - 0.5f,
        Random.nextFloat() - 0.5f
    ).normalize()
    private var acceleration: Vector = Vector(0f, 0f)

    fun applyForce(force: Vector) {
        acceleration.add(force)
    }

    fun update() {
        velocity.add(acceleration)
        position.add(velocity)
        acceleration.mult(0f)
    }
}
