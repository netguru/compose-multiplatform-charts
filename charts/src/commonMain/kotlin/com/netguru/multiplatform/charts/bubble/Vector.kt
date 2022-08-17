package com.netguru.multiplatform.charts.bubble

import kotlin.math.sqrt

data class Vector(var x: Float, var y: Float) {
    fun mag(): Float {
        return sqrt((magSq().toDouble()).toFloat())
    }

    private fun magSq(): Float {
        return x * x + y * y
    }

    fun add(v: Vector): Vector {
        x += v.x
        y += v.y
        return this
    }

    fun sub(v: Vector): Vector {
        x -= v.x
        y -= v.y
        return this
    }

    operator fun set(x: Float, y: Float): Vector {
        this.x = x
        this.y = y
        return this
    }

    fun mult(n: Float): Vector {
        x *= n
        y *= n
        return this
    }

    operator fun div(n: Float): Vector {
        x /= n
        y /= n
        return this
    }

    fun normalize(): Vector {
        val m = mag()
        if (m != 0f && m != 1f) {
            div(m)
        }
        return this
    }

    fun limit(max: Float): Vector {
        if (magSq() > max * max) {
            normalize()
            mult(max)
        }
        return this
    }

    fun setMag(len: Float): Vector {
        normalize()
        mult(len)
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector) {
            return false
        }
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    companion object {
        fun sub(v1: Vector, v2: Vector): Vector = Vector(v1.x - v2.x, v1.y - v2.y)

        fun dist(v1: Vector, v2: Vector): Float {
            val dx = v1.x - v2.x
            val dy = v1.y - v2.y
            return sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        }
    }
}
