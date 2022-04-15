package com.netguru.charts.bubblechart

import androidx.compose.ui.geometry.Size
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

/*
This class implements simple circle packing algorithm based on simple physics engine.
At the beginning all circles are put in the center. Collisions are checked between each circle.
If two circles collide, collision forces are applied and circles are moved. This check is done for every circle.
Algorithm stops when none of the circles collides.
 */
class BubblePacking(
    data: List<Bubble>,
    private val containerSize: Size,
    private val distanceBetweenBubbles: Float = 0f
) {
    private val bubbles = data.toMutableList()
    private var shouldRun = 1

    init {
        placeBubblesInCenter()
    }

    private fun placeBubblesInCenter() = bubbles.forEach {
        it.position = Vector(containerSize.width / 2f, containerSize.height / 2f)
    }

    private fun checkBorders(bubbleIndex: Int) {
        val bubble = bubbles[bubbleIndex]
        if (bubble.position.x - bubble.radius < 0f) {
            bubble.position.x = bubble.radius
        } else if (bubble.position.x + bubble.radius > containerSize.width) {
            bubble.position.x = containerSize.width - bubble.radius
        }
        if (bubble.position.y - bubble.radius < 0f) {
            bubble.position.y = bubble.radius
        } else if (bubble.position.y + bubble.radius > containerSize.height) {
            bubble.position.y = containerSize.height - bubble.radius
        }
    }

    private fun applySeparationForcesToBubbles(
        bubbleIndex: Int,
        separateForces: Array<Vector>,
        nearBubbles: Array<Int>
    ) {
        val bubbleI = bubbles[bubbleIndex]

        for (j in bubbleIndex + 1 until bubbles.size) {
            val bubbleJ = bubbles[j]
            val forceIJ = getSeparationForce(bubbleI, bubbleJ)

            if (forceIJ.mag() > 0) {
                separateForces[bubbleIndex].add(forceIJ)
                separateForces[j].sub(forceIJ)
                nearBubbles[bubbleIndex]++
                nearBubbles[j]++
            }
        }

        if (nearBubbles[bubbleIndex] > 0) {
            separateForces[bubbleIndex].div(nearBubbles[bubbleIndex].toFloat())
        }

        if (separateForces[bubbleIndex].mag() > 0f) {
            separateForces[bubbleIndex].setMag(MAX_SPEED)
            separateForces[bubbleIndex].sub(bubbles[bubbleIndex].velocity)
            separateForces[bubbleIndex].limit(MAX_FORCE)
        }

        val separation = separateForces[bubbleIndex]
        bubbles[bubbleIndex].applyForce(separation)
        bubbles[bubbleIndex].update()

        shouldRun += if (isRunning()) 1 else -1
        bubbleI.velocity.x = 0f
        bubbleI.velocity.y = 0f
    }

    private fun getSeparationForce(b1: Bubble, b2: Bubble): Vector {
        val steerForce = Vector(0f, 0f)
        val distance = Vector.dist(b1.position, b2.position)

        if ((distance > 0f) && (distance < b1.radius + b2.radius + distanceBetweenBubbles)) {
            val diff = Vector.sub(b1.position, b2.position)
            diff.normalize()
            diff.div(distance)
            steerForce.add(diff)
        }
        return steerForce
    }

    private fun isRunning() = bubbles.any {
        abs(it.velocity.x) > 0.01f && abs(it.velocity.y) > 0.01f
    }

    private fun setRandomVelocities() = bubbles.forEach {
        it.velocity = Vector(Random.nextFloat() - 0.5f, Random.nextFloat() - 0.5f)
    }

    fun pack(): List<Bubble> {
        setRandomVelocities()
        var timeOut = TIMEOUT_ITERATIONS

        while (shouldRun > 0) {
            val separateForces = Array(bubbles.size) { Vector(0f, 0f) }
            val nearBubbles = Array(bubbles.size) { 0 }

            for (i in bubbles.indices) {
                checkBorders(bubbleIndex = i)
                applySeparationForcesToBubbles(bubbleIndex = i, separateForces, nearBubbles)
            }

            timeOut--
            if (timeOut <= 0) shouldRun = 0
        }

        resize()

        return bubbles
    }

    private fun resize() {
        var minX = containerSize.width
        var maxX = 0f
        var minY = containerSize.height
        var maxY = 0f

        bubbles.forEach {
            val x = it.position.x
            val y = it.position.y

            if (x - it.radius < minX) minX = x - it.radius
            if (x + it.radius > maxX) maxX = x + it.radius
            if (y - it.radius < minY) minY = y - it.radius
            if (y + it.radius > maxY) maxY = y + it.radius
        }

        val diffX = maxX - minX
        val diffY = maxY - minY
        val biggestSide = max(diffX, diffY)
        val scale = containerSize.minDimension / biggestSide

        val offsetX = minX * scale
        val offsetY = minY * scale

        bubbles.forEach {
            it.position.mult(scale).sub(Vector(offsetX, offsetY))
            it.radius *= scale
        }
    }

    companion object {
        const val TIMEOUT_ITERATIONS = 5000
        const val MAX_SPEED = 1f
        const val MAX_FORCE = 1f
    }
}
