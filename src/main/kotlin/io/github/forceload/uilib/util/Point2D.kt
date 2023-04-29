package io.github.forceload.uilib.util

data class Point2D<T1: Number, T2: Number>(var x: T1, var y: T2) {
    override fun equals(other: Any?): Boolean {
        return if (other is Point2D<*, *>) {
            hashCode() == other.hashCode()
        } else { false }
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T3 : Number, T4 : Number> minus(other: Point2D<T3, T4>): Point2D<T1, T2> {
        val tempX: T1
        val tempY: T2

        if (this.x is Int && other.x is Int && this.y is Int && other.y is Int) {
            tempX = ((this.x as Int) - (other.x as Int)) as T1
            tempY = ((this.y as Int) - (other.y as Int)) as T2
        } else {
            tempX = (this.x.toDouble() - other.x.toDouble()) as T1
            tempY = (this.y.toDouble() - other.y.toDouble()) as T2
        }

        return Point2D(tempX, tempY)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun times(other: Int): Point2D<T1, T2> {
        val tempX: T1 = ((this.x as Int) - (other)) as T1
        val tempY: T2 = ((this.y as Int) - (other)) as T2

        return Point2D(tempX, tempY)
    }
}
